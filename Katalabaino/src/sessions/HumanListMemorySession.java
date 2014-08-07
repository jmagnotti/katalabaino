package sessions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import core.session.Session;
import core.trial.Colors;
import core.trial.Shapes;
import core.trial.Stimulus;
import core.trial.Trial;
import file.ConnectionCloser;

public class HumanListMemorySession extends Session
{

	public HumanListMemorySession()
	{
		super();
	}

	@Override
	public Session fromMDB(Connection session, Connection results, String sName, String rName)
			throws SQLException
	{
		HumanListMemorySession lms = new HumanListMemorySession();

		lms.comment = "List Memory";

		applySessionInfo(lms, results, sName, rName);

		Statement ses = session.createStatement();
		ses.execute("Select * from ProbeInfo");
		ResultSet rs = ses.getResultSet();

		Vector<Integer> probeDelays = new Vector<Integer>();
		Vector<Stimulus> probes = new Vector<Stimulus>();
		while (rs.next()) {
			probeDelays.add(rs.getInt("Delay"));
			probes.add(new Stimulus(rs.getString("Filename"), 0, Colors.KSCOPE, Shapes.KSCOPE,
					"KSCOPE:KSCOPE"));
		}
		rs.close();
		ses.close();

		ses = session.createStatement();
		ses.execute("Select * from StimuliInfo ORDER BY TrialNum, Position ASC");
		rs = ses.getResultSet();
		HashMap<Integer, Vector<Stimulus>> samples = new HashMap<Integer, Vector<Stimulus>>();
		int tnum = -1, vt = -1, isi = -1;
		while (rs.next()) {
			tnum = rs.getInt("TrialNum");

			if (!samples.containsKey(tnum)) {
				samples.put(tnum, new Vector<Stimulus>());
			}

			samples.get(tnum).add(
					new Stimulus(rs.getString("Filename"), rs.getInt("Position"), Colors.KSCOPE,
							Shapes.KSCOPE, "KSCOPE:KSCOPE"));

			if (vt == -1) {
				vt = rs.getInt("ViewTime");
				isi = rs.getInt("ISI");
			}

		}
		rs.close();
		ses.close();

		Statement res = results.createStatement();
		res = results.createStatement();
		res.execute("Select * from trialResults");
		rs = res.getResultSet();
		while (rs.next()) {
			Trial t = new Trial();
			t.trialNumber = rs.getInt("TrialNum");
			t.trialType = rs.getString("TrialType");
			t.configuration = t.trialType;

			t.sampleSetSize = rs.getInt("ListLength");
			t.choiceSetSize = 1;

			t.response = rs.getString("Response");
			t.responseTime = rs.getDouble("ResponseTime");
			t.isTransfer = rs.getBoolean("Transfer");
			t.correctResponse = t.trialType;
			t.correctLocation = rs.getInt("CorrectPosition");

			t.incorrectCorrections = rs.getInt("IncorrectCorrections");

			t.intertrialInterval = rs.getInt("ITI");
			t.probeDelay = probeDelays.get(t.trialNumber - 1);
			t.viewTime = vt;

			t.actualViewTime = t.viewTime;

			for (int i = 0; i < samples.get(t.trialNumber).size(); i++) {
				t.sampleStimuli.add(samples.get(t.trialNumber).get(i));
			}

			t.choiceStimuli.add(probes.get(t.trialNumber-1));

			lms.trials.add(t);
		}

		rs.close();
		res.close();

		ConnectionCloser.shutIt(session);
		ConnectionCloser.shutIt(results);

		return lms;
	}

	@Override
	public Session fromXML(File xmlFile) throws FileNotFoundException, SAXException, IOException,
			ParserConfigurationException
	{
		return new HumanListMemorySession(xmlFile);
	}

	public HumanListMemorySession(File f) throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException
	{
		this(new FileInputStream(f));
	}

	public HumanListMemorySession(InputStream is) throws ParserConfigurationException,
			SAXException, IOException
	{
		super(is);
	}

	@Override
	public Session fromStream(InputStream stream) throws FileNotFoundException, SAXException,
			IOException, ParserConfigurationException
	{
		return new HumanListMemorySession(stream);
	}

}

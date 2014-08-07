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
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import core.session.Session;
import core.trial.SampleResponse;
import core.trial.Stimulus;
import core.trial.Trial;
import file.ConnectionCloser;

public class N_ItemMTSSession extends Session
{

	public static final int	CENTER_POSITION	= 99;

	public N_ItemMTSSession()
	{
		super();
	}

	public N_ItemMTSSession(File xmlFile) throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException
	{
		this(new FileInputStream(xmlFile));
	}

	public N_ItemMTSSession(InputStream is) throws ParserConfigurationException, SAXException,
			IOException
	{
		super(is);
	}

	@Override
	public N_ItemMTSSession fromMDB(Connection session, Connection results, String sName,
			String rName) throws SQLException
	{
		N_ItemMTSSession nimtsSession = new N_ItemMTSSession();

		nimtsSession.comment = "N-Item Match-to-Sample";

		applySessionInfo(nimtsSession, results, sName, rName);

		Vector<Integer> probeDelays = new Vector<Integer>();
		Vector<Vector<Stimulus>> probes = new Vector<Vector<Stimulus>>();
		Statement ses = session.createStatement();
		ses.execute("Select ProbeInfo.TrialNum as tNum, StimuliInfo.Filename as sfn, ProbeInfo.Filename as pfn, Filename2, Filename3, "
				+ "Filename4, Filename5, Delay, POS1, POS2, POS3, POS4, POS5, POS6, trialType, "
				+ "POS6 from ProbeInfo, TrialInfo, StimuliInfo where ProbeInfo.TrialNum = TrialInfo.TrialNum AND TrialInfo.TrialNum = StimuliInfo.TrialNum "
				+ "ORDER by TrialInfo.TrialNum ASC");

		ResultSet rs = ses.getResultSet();
		while (rs.next()) {
			probes.add(new Vector<Stimulus>());

			probeDelays.add(rs.getInt("Delay"));

			probes.lastElement().add(new Stimulus(rs.getString("sfn"), rs.getInt("POS1")));
			probes.lastElement().add(new Stimulus(rs.getString("pfn"), rs.getInt("POS2")));

			int displaySize = Integer.parseInt(rs.getString("trialType").substring(2));

			for (int i = 3; i <= displaySize; i++) {
				probes.lastElement().add(
						new Stimulus(rs.getString("Filename" + (i - 1)), Integer.parseInt(rs
								.getString("POS" + i))));
			}
		}

		// System.out.println("PD Size: " + probeDelays.size());

		rs.close();
		ses.close();

		ses = session.createStatement();
		ses.execute("Select StimTouches from SessionInfo");
		int obsResp = 0;
		rs = ses.getResultSet();
		while (rs.next())
			obsResp = rs.getInt("StimTouches");

		Statement res = results.createStatement();

		Vector<Vector<SampleResponse>> responses = new Vector<Vector<SampleResponse>>();
		Vector<Vector<SampleResponse>> corrTrialResponses = new Vector<Vector<SampleResponse>>();

		for (int i = 0; i < probeDelays.size(); i++) {
			responses.add(new Vector<SampleResponse>());
			corrTrialResponses.add(new Vector<SampleResponse>());
		}

		// go back through and add all the stimulus pecks
		res.execute("Select * from StimResponses WHERE correctionTrial = 0 ORDER BY TrialNum ASC, CorrectionTrial ASC, ResponseTime ASC");
		rs = res.getResultSet();
		while (rs.next()) {
			responses.get(rs.getInt("TrialNum") - 1).add(
					new SampleResponse(rs.getInt("ListPosition"), rs.getInt("ResponseTime"), rs
							.getInt("CorrectionTrial")));
		}
		rs.close();
		res.close();

		res = results.createStatement();
		res.execute("Select * from StimResponses WHERE correctionTrial > 0 ORDER BY TrialNum ASC, CorrectionTrial ASC, ResponseTime ASC");
		rs = res.getResultSet();
		while (rs.next()) {
			corrTrialResponses.get(rs.getInt("TrialNum") - 1).add(
					new SampleResponse(rs.getInt("ListPosition"), rs.getInt("ResponseTime"), rs
							.getInt("CorrectionTrial")));
		}
		rs.close();
		res.close();

		res = results.createStatement();
		res.execute("Select * from trialResults");
		rs = res.getResultSet();
		while (rs.next()) {
			Trial t = new Trial();
			t.trialNumber = rs.getInt("TrialNum");
			t.trialType = rs.getString("TrialType");
			t.configuration = rs.getString("Configuration");

			t.choiceSetSize = Integer.parseInt(t.trialType.substring(2));
			t.sampleSetSize = 1;

			t.response = rs.getString("Response");
			t.responseTime = rs.getDouble("ResponseTime");
			t.isTransfer = rs.getBoolean("Transfer");
			t.correctResponse = rs.getString("Item4");
			t.correctLocation = Integer.parseInt(t.correctResponse);
			t.incorrectCorrections = rs.getInt("IncorrectCorrections");

			t.intertrialInterval = rs.getInt("ITI");
			t.probeDelay = probeDelays.get(t.trialNumber - 1);

			t.observingResponse = obsResp;

			t.sampleResponses = responses.get(t.trialNumber - 1);
			t.correctionTrialSampleResponses = corrTrialResponses.get(t.trialNumber - 1);

			t.viewTime = (int) t.sampleResponses.lastElement().responseTime;

			t.actualViewTime = t.viewTime;

			t.sampleStimuli.add(new Stimulus(rs.getString("Item1"),
					N_ItemMTSSession.CENTER_POSITION));

			// probe items
			t.choiceStimuli.addAll(probes.get(t.trialNumber - 1));

			nimtsSession.trials.add(t);
		}

		rs.close();
		res.close();

		ConnectionCloser.shutIt(session);
		ConnectionCloser.shutIt(results);

		return nimtsSession;
	}

	@Override
	public N_ItemMTSSession fromXML(File xmlFile) throws FileNotFoundException, SAXException,
			IOException, ParserConfigurationException
	{
		return new N_ItemMTSSession(xmlFile);
	}

	@Override
	public N_ItemMTSSession fromStream(InputStream stream) throws FileNotFoundException,
			SAXException, IOException, ParserConfigurationException
	{
		return new N_ItemMTSSession(stream);
	}

}

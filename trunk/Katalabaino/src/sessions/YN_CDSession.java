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

import core.Colors;
import core.ConnectionCloser;
import core.SampleResponse;
import core.Session;
import core.Shapes;
import core.Stimulus;
import core.Trial;

public class YN_CDSession extends Session
{

	public YN_CDSession()
	{
		super();
	}

	public YN_CDSession(File f) throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException
	{
		this(new FileInputStream(f));
	}

	public YN_CDSession(InputStream is) throws ParserConfigurationException, SAXException,
			IOException
	{
		super(is);
	}

	@Override
	public Session fromMDB(Connection session, Connection results, String sName, String rName)
			throws SQLException
	{
//		System.out.println(sName + ":" + rName);		
		YN_CDSession yncds = new YN_CDSession();

		yncds.comment = "Yes/No Change Detection";

		applySessionInfo(yncds, results, sName, rName);

		Vector<Vector<SampleResponse>> responses = new Vector<Vector<SampleResponse>>();
		Vector<Vector<SampleResponse>> corrTrialResponses = new Vector<Vector<SampleResponse>>();

		Statement res = results.createStatement();
		res.execute("Select Count(*) as count from TrialResults");
		ResultSet rs = res.getResultSet();
		int tCount = -1;
		while (rs.next())
			tCount = rs.getInt("count");
		rs.close();
		res.close();

		// initialize the peck vectors
		for (int i = 0; i < tCount; i++) {
			responses.add(new Vector<SampleResponse>());
			corrTrialResponses.add(new Vector<SampleResponse>());
		}

		res = results.createStatement();
		// go through and add all the stimulus pecks
		res.execute("Select * from StimResponses WHERE CorrectionTrialNumber = 0 ORDER BY TrialNumber ASC, CorrectionTrialNumber ASC");
		rs = res.getResultSet();

		while (rs.next()) {
			int vta = 0;
			try {
				vta = rs.getInt("ViewTimeAbort");
			}
			catch (SQLException sqle) {
				vta = -1;
			}

			
			try {
			
			responses.get(rs.getInt("TrialNumber") - 1).add(
					new SampleResponse(rs.getInt("StimulusNumber"), rs.getInt("ResponseTime"), rs
							.getInt("CorrectionTrialNumber"), vta, rs.getInt("xPosition"), rs
							.getInt("yPosition")));
			} catch (Exception e) {
				System.err.println("caught");
			}
		}
		rs.close();
		res.close();

		res = results.createStatement();
		res.execute("Select * from StimResponses WHERE CorrectionTrialNumber > 0 ORDER BY TrialNumber ASC, CorrectionTrialNumber ASC");
		rs = res.getResultSet();
		while (rs.next()) {
			int vta = 0;
			try {
				vta = rs.getInt("ViewTimeAbort");
			}
			catch (SQLException sqle) {
				vta = -1;
			}

			corrTrialResponses.get(rs.getInt("TrialNumber") - 1).add(
					new SampleResponse(rs.getInt("StimulusNumber"), rs.getInt("ResponseTime"), rs
							.getInt("CorrectionTrialNumber"), vta, rs.getInt("xPosition"), rs
							.getInt("yPosition")));
		}
		rs.close();
		res.close();

		res = results.createStatement();
		res.execute("Select * from TrialResults ORDER BY TrialNumber ASC");
		rs = res.getResultSet();

		while (rs.next()) {
			Trial t = new Trial();

			t.trialNumber = rs.getInt("TrialNumber");
			t.trialType = rs.getString("TrialType");
			t.configuration = rs.getString("Configuration");

			t.sampleSetSize = rs.getInt("SampleDisplaySize");

			// t.choiceSetSize = rs.getInt("ProbeDisplaySize");
			t.choiceSetSize = 1; // always set to 1 for now

			t.response = rs.getString("Response");
			t.responseLocation = rs.getInt("ResponseLocation");
			t.responseTime = rs.getDouble("ResponseTime");
			t.isTransfer = rs.getBoolean("isTransfer");
			t.correctResponse = t.trialType;

			t.correctLocation = rs.getInt("ChangePosition");

			t.incorrectCorrections = rs.getInt("IncorrectCorrections");

			t.intertrialInterval = rs.getInt("ITI");
			t.probeDelay = rs.getInt("ProbeDelay");
			t.viewTime = rs.getInt("ViewingTime");

			t.sampleResponses = responses.get(t.trialNumber - 1);
			t.correctionTrialSampleResponses = corrTrialResponses.get(t.trialNumber - 1);

			t.observingResponse = rs.getInt("ObservingResponse");

			t.actualViewTime = t.viewTime;

			int nSampleItems = t.sampleSetSize < 1 ? 1 : t.sampleSetSize;

			for (int i = 1; i <= nSampleItems; i++) {
				// System.out.println("Trying: " + i);
				String iName = rs.getString("Sample" + i);
				if (iName != null)
					t.sampleStimuli.add(new Stimulus(iName, rs.getInt("Sample" + i + "Position"),
							Colors.CLIP_ART, Shapes.RECT_IMAGE, "CLIPART:"
									+ iName.substring(0, iName.indexOf("."))));
			}

			// choice items
			int nChoiceItems = t.choiceSetSize < 1 ? 1 : t.choiceSetSize;
			for (int i = 1; i <= nChoiceItems; i++) {
				String iName = rs.getString("Probe" + i);
				if (iName != null)
					t.choiceStimuli.add(new Stimulus(iName, rs.getInt("Probe" + i + "Position"),
							Colors.CLIP_ART, Shapes.RECT_IMAGE, "CLIPART:"
									+ iName.substring(0, iName.indexOf("."))));
			}

			yncds.trials.add(t);
		}

		rs.close();
		res.close();

		// ConnectionCloser.shutIt(session);
		ConnectionCloser.shutIt(results);

		return yncds;
	}

	@Override
	public Session fromXML(File xmlFile) throws FileNotFoundException, SAXException, IOException,
			ParserConfigurationException
	{
		return new YN_CDSession(xmlFile);
	}

	@Override
	public Session fromStream(InputStream stream) throws FileNotFoundException, SAXException,
			IOException, ParserConfigurationException
	{
		return new YN_CDSession(stream);
	}

}

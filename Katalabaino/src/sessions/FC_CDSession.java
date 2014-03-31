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

public class FC_CDSession extends Session implements Comparable<Session> {
	public FC_CDSession() {
		super();
	}

	public FC_CDSession(File f) throws SAXException, IOException, ParserConfigurationException {
		this(new FileInputStream(f));
	}

	/**
	 * @param is
	 *            InputStream to a properly formatted XML file
	 * @param name
	 *            the name of the XML file
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public FC_CDSession(InputStream is) throws SAXException, IOException, ParserConfigurationException {
		super(is);
	}

	public FC_CDSession fromMDB(Connection session, Connection results, String sName, String rName) throws SQLException {
		FC_CDSession fc_cdSession = new FC_CDSession();

		fc_cdSession.comment = "Forced-Choice Change Detection";

		applySessionInfo(fc_cdSession, results, sName, rName);

		Statement ses = session.createStatement();
		ses.execute("Select * from ProbeInfo");
		ResultSet rs = ses.getResultSet();

		Vector<Integer> probeDelays = new Vector<Integer>();
		while (rs.next()) {
			probeDelays.add(rs.getInt("Delay"));
		}
		rs.close();
		ses.close();

		ses = session.createStatement();
		ses.execute("Select * from StimuliInfo");
		rs = ses.getResultSet();
		Vector<Integer> viewTimes = new Vector<Integer>();
		while (rs.next()) {
			viewTimes.add(rs.getInt("ViewTime"));
		}
		rs.close();
		ses.close();

		Statement res = results.createStatement();

		Vector<Vector<SampleResponse>> responses = new Vector<Vector<SampleResponse>>();
		Vector<Vector<SampleResponse>> corrTrialResponses = new Vector<Vector<SampleResponse>>();

		for (int i = 0; i < viewTimes.size(); i++) {
			responses.add(new Vector<SampleResponse>());
			corrTrialResponses.add(new Vector<SampleResponse>());
		}

		// go back through and add all the stimulus pecks
		res.execute("Select * from StimResponses WHERE correctionTrial = 0 ORDER BY TrialNum ASC, CorrectionTrial ASC");
		rs = res.getResultSet();
		while (rs.next()) {
			if (rs.getMetaData().getColumnCount() < 5) {
				responses.get(rs.getInt("TrialNum") - 1).add(
						new SampleResponse(rs.getInt("ListPosition"), rs.getInt("ResponseTime"), rs
								.getInt("CorrectionTrial"), -1, -1));

			} else {
				responses.get(rs.getInt("TrialNum") - 1).add(
						new SampleResponse(rs.getInt("ListPosition"), rs.getInt("ResponseTime"), rs
								.getInt("CorrectionTrial"), rs.getInt("xpos"), rs.getInt("ypos")));
			}
		}
		rs.close();
		res.close();

		res = results.createStatement();
		res.execute("Select * from StimResponses WHERE correctionTrial > 0 ORDER BY TrialNum ASC, CorrectionTrial ASC");
		rs = res.getResultSet();
		while (rs.next()) {

			if (rs.getMetaData().getColumnCount() < 5) {
				corrTrialResponses.get(rs.getInt("TrialNum") - 1).add(
						new SampleResponse(rs.getInt("ListPosition"), rs.getInt("ResponseTime"), rs
								.getInt("CorrectionTrial"), -1, -1));
			} else {
				corrTrialResponses.get(rs.getInt("TrialNum") - 1).add(
						new SampleResponse(rs.getInt("ListPosition"), rs.getInt("ResponseTime"), rs
								.getInt("CorrectionTrial"), rs.getInt("xpos"), rs.getInt("ypos")));
			}
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

			t.sampleSetSize = Integer.parseInt(t.configuration);
			t.choiceSetSize = 2;

			t.response = rs.getString("Response");
			t.responseTime = rs.getDouble("ResponseTime");
			t.isTransfer = rs.getBoolean("Transfer");
			t.correctResponse = rs.getString("CorrectPosition");
			t.correctLocation = Integer.parseInt(t.correctResponse);

			t.incorrectCorrections = rs.getInt("IncorrectCorrections");

			t.intertrialInterval = rs.getInt("ITI");
			t.probeDelay = probeDelays.get(t.trialNumber - 1);
			t.viewTime = viewTimes.get(t.trialNumber - 1);

			t.sampleResponses = responses.get(t.trialNumber - 1);
			t.correctionTrialSampleResponses = corrTrialResponses.get(t.trialNumber - 1);

			// this is for TRUE DRO condition, this won't work if the data are
			// from the cumulative
			// peck penalty days
			if (t.sampleResponses.size() > 0)
				t.actualViewTime = t.viewTime + (int) t.sampleResponses.lastElement().responseTime;
			else
				t.actualViewTime = t.viewTime;

			for (int i = 1; i <= t.sampleSetSize; i++) {
				t.sampleStimuli.add(new Stimulus(rs.getString("Item" + i), rs.getInt("POS" + i)));
			}

			// changed item
			t.choiceStimuli.add(new Stimulus(rs.getString("ItemP2"), t.sampleStimuli.get(0).position));

			// unchanged item
			if (t.sampleSetSize > 1) {
				t.choiceStimuli.add(new Stimulus(rs.getString("ItemP"), t.sampleStimuli.get(1).position));
			} else {
				t.choiceStimuli.add(new Stimulus("NULL", 0, Colors.NONE, Shapes.NONE, "empty"));
			}

			fc_cdSession.trials.add(t);
		}

		rs.close();
		res.close();

		ConnectionCloser.shutIt(session);
		ConnectionCloser.shutIt(results);

		return fc_cdSession;
	}

	@Override
	public Session fromXML(File xmlFile) throws SAXException, IOException, ParserConfigurationException {
		return new FC_CDSession(xmlFile);
	}

	@Override
	public Session fromStream(InputStream stream) throws FileNotFoundException, SAXException, IOException,
			ParserConfigurationException {
		return new FC_CDSession(stream);
	}

}

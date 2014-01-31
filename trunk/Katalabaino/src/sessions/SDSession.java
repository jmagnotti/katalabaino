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
import java.util.NoSuchElementException;
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

public class SDSession extends Session {

	public SDSession() {
		super();
	}

	@Override
	public Session fromMDB(Connection session, Connection results,
			String sName, String rName) throws SQLException {
		SDSession psds = new SDSession();

		psds.comment = "Same/Different";

		applySessionInfo(psds, results, sName, rName);

		Statement ses = session.createStatement();
		ses.execute("Select * from ProbeInfo");
		ResultSet rs = ses.getResultSet();

		Vector<Integer> probeDelays = new Vector<Integer>();
		Vector<Stimulus> probes = new Vector<Stimulus>();
		while (rs.next()) {
			probeDelays.add(rs.getInt("Delay"));
			probes.add(new Stimulus(rs.getString("Filename"), 0,
					Colors.TRAVEL_SLIDE, Shapes.TRAVEL_SLIDE,
					"TRAVEL_SLIDE:TRAVEL_SLIDE"));
		}
		rs.close();
		ses.close();

		ses = session.createStatement();
		ses.execute("Select * from StimuliInfo ORDER BY TrialNum, Position ASC");
		rs = ses.getResultSet();
		HashMap<Integer, Vector<Stimulus>> samples = new HashMap<Integer, Vector<Stimulus>>();
		int tnum = -1, viewTime = -1, isi = -1;
		while (rs.next()) {
			tnum = rs.getInt("TrialNum");

			samples.put(tnum, new Vector<Stimulus>());

			samples.get(tnum).add(
					new Stimulus(rs.getString("Filename"), rs
							.getInt("Position"), Colors.TRAVEL_SLIDE,
							Shapes.TRAVEL_SLIDE, "TRAVEL_SLIDE:TRAVEL_SLIDE"));

			if (viewTime == -1) {
				viewTime = rs.getInt("ViewTime");
				isi = rs.getInt("ISI");
			}

		}
		rs.close();
		ses.close();

		Statement res = results.createStatement();

		Vector<Vector<SampleResponse>> responses = new Vector<Vector<SampleResponse>>();
		Vector<Vector<SampleResponse>> corrTrialResponses = new Vector<Vector<SampleResponse>>();

		for (int i = 0; i < probeDelays.size(); i++) {
			responses.add(new Vector<SampleResponse>());
			corrTrialResponses.add(new Vector<SampleResponse>());
		}

		// go back through and add all the stimulus pecks
		res.execute("Select * from StimResponses WHERE correctionTrial = 0 ORDER BY TrialNum ASC, CorrectionTrial ASC");
		rs = res.getResultSet();
		while (rs.next()) {
			responses.get(rs.getInt("TrialNum") - 1).add(
					new SampleResponse(rs.getInt("ListPosition"), rs
							.getInt("ResponseTime"), rs
							.getInt("CorrectionTrial")));
		}
		rs.close();
		res.close();

		res = results.createStatement();
		res.execute("Select * from StimResponses WHERE correctionTrial > 0 ORDER BY TrialNum ASC, CorrectionTrial ASC");
		rs = res.getResultSet();
		while (rs.next()) {
			corrTrialResponses.get(rs.getInt("TrialNum") - 1).add(
					new SampleResponse(rs.getInt("ListPosition"), rs
							.getInt("ResponseTime"), rs
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

			// System.out.println(t.trialNumber);

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

			t.sampleResponses = responses.get(t.trialNumber - 1);
			t.correctionTrialSampleResponses = corrTrialResponses
					.get(t.trialNumber - 1);

			try {
				t.viewTime = (int) t.sampleResponses.lastElement().responseTime;
			} catch (NoSuchElementException npe) {
				// just eat the error for now
				t.viewTime = -99;
			}
			t.actualViewTime = t.viewTime;

			for (int i = 0; i < samples.get(t.trialNumber).size(); i++) {
				t.sampleStimuli.add(samples.get(t.trialNumber).get(i));
			}

			t.choiceStimuli.add(probes.get(t.trialNumber - 1));

			psds.trials.add(t);
		}

		rs.close();
		res.close();

		ConnectionCloser.shutIt(session);
		ConnectionCloser.shutIt(results);

		return psds;
	}

	@Override
	public Session fromXML(File xmlFile) throws FileNotFoundException,
			SAXException, IOException, ParserConfigurationException {
		return new SDSession(xmlFile);
	}

	public SDSession(File f) throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException {
		this(new FileInputStream(f));
	}

	public SDSession(InputStream is) throws ParserConfigurationException,
			SAXException, IOException {
		super(is);
	}

	@Override
	public Session fromStream(InputStream stream) throws FileNotFoundException,
			SAXException, IOException, ParserConfigurationException {
		return new SDSession(stream);
	}

}

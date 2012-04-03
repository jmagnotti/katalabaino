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

import core.Colors;
import core.ConnectionCloser;
import core.SampleResponse;
import core.Session;
import core.Shapes;
import core.Stimulus;
import core.Trial;

public class HoustonHumanCDSession extends Session implements Comparable<Session>
{

	private int getColorID(String fname)
	{
		if (fname.startsWith("c")) return Colors.CLIP_ART;
		if (fname.startsWith("k")) return Colors.KSCOPE;
		if (fname.startsWith("s")) return Colors.SNODGRASS;
		if (fname.startsWith("j"))
			return Colors.KANJI;

		else
			return -1;
	}

	private int getShapeID(String fname)
	{
		if (fname.startsWith("c")) return Shapes.CLIP_ART;
		if (fname.startsWith("k")) return Shapes.KSCOPE;
		if (fname.startsWith("s")) return Shapes.SNODGRASS;
		if (fname.startsWith("j"))
			return Shapes.KANJI;

		else
			return -1;
	}

	public HoustonHumanCDSession()
	{

	}

	public HoustonHumanCDSession(File f) throws SAXException, IOException,
			ParserConfigurationException
	{
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
	public HoustonHumanCDSession(InputStream is) throws SAXException, IOException,
			ParserConfigurationException
	{
		super(is);
	}

	public HoustonHumanCDSession fromMDB(Connection session, Connection results, String sName,
			String rName) throws SQLException
	{

		System.out.println("Working on: " + sName);
		HoustonHumanCDSession houHumanCDSession = new HoustonHumanCDSession();

		houHumanCDSession.comment = "Houston Forced-Choice Change Detection";

		applySessionInfo(houHumanCDSession, results, sName, rName);

		Statement ses = session.createStatement();
		ses.execute("Select * from ProbeInfo");
		ResultSet rs = ses.getResultSet();

		Vector<Integer> probeDelays = new Vector<Integer>();
		HashMap<Integer, Integer> sampleSetSizes = new HashMap<Integer, Integer>();
		HashMap<Integer, Vector<Integer>> positions = new HashMap<Integer, Vector<Integer>>();
		HashMap<Integer, Vector<Stimulus>> stims = new HashMap<Integer, Vector<Stimulus>>();
		HashMap<Integer, Stimulus> changedItems = new HashMap<Integer, Stimulus>();

		while (rs.next()) {
			probeDelays.add(rs.getInt("Delay"));
		}
		rs.close();
		ses.close();

		ses = session.createStatement();
		ses.execute("Select * from TrialInfo");
		rs = ses.getResultSet();
		while (rs.next()) {
			int tnum = rs.getInt("TrialNum");

			positions.put(tnum, new Vector<Integer>());
			stims.put(tnum, new Vector<Stimulus>());

			sampleSetSizes.put(tnum, rs.getInt("SetSize"));

			for (int i = 1; i <= sampleSetSizes.get(tnum); i++) {
				positions.get(tnum).add(rs.getInt("POS" + i));
			}
		}
		rs.close();
		ses.close();

		// System.out.println("SSS: " + sampleSetSizes.size());

		ses = session.createStatement();
		ses.execute("Select * from StimuliInfo");
		rs = ses.getResultSet();
		Vector<Integer> viewTimes = new Vector<Integer>();
		while (rs.next()) {
			int tnum = rs.getInt("TrialNum");
			viewTimes.add(rs.getInt("ViewTime"));
			String fname = rs.getString("Filename");
			stims.get(tnum).add(
					new Stimulus(fname, positions.get(tnum).get(0), getColorID(fname),
							getShapeID(fname), "temp:temp"));
		}
		rs.close();
		ses.close();

		ses = session.createStatement();
		ses.execute("Select * from ProbeInfo");
		rs = ses.getResultSet();
		while (rs.next()) {
			int tnum = rs.getInt("TrialNum");
			// System.out.println("trial: " + tnum);
			for (int i = 1; i < sampleSetSizes.get(tnum); i++) {
				String fname = "";
				if (i == 1)
					fname = rs.getString("Filename");
				else
					fname = rs.getString("Filename" + i);

				// System.out.println("Pos Len: " + positions.get(tnum).size() + ", sss: "
				// + sampleSetSizes.get(tnum) + ", fname: " + fname);

				stims.get(tnum).add(
						new Stimulus(fname, positions.get(tnum).get(i), getColorID(fname),
								getShapeID(fname), "temp:temp"));
			}

			// grabbed the changed item
			int i = sampleSetSizes.get(tnum);
			String fname = rs.getString("Filename" + i);
			changedItems.put(tnum, new Stimulus(fname, positions.get(tnum).get(0),
					getColorID(fname), getShapeID(fname), "temp:temp"));

		}
		rs.close();
		ses.close();

		Statement res = results.createStatement();

		// Vector<Vector<SampleResponse>> responses = new Vector<Vector<SampleResponse>>();
		// Vector<Vector<SampleResponse>> corrTrialResponses = new Vector<Vector<SampleResponse>>();
		//
		// for (int i = 0; i < viewTimes.size(); i++) {
		// responses.add(new Vector<SampleResponse>());
		// corrTrialResponses.add(new Vector<SampleResponse>());
		// }
		//
		// // go back through and add all the stimulus pecks
		// res.execute("Select * from StimResponses WHERE correctionTrial = 0 ORDER BY TrialNum ASC, CorrectionTrial ASC");
		// rs = res.getResultSet();
		// while (rs.next()) {
		// responses.get(rs.getInt("TrialNum") - 1).add(
		// new SampleResponse(rs.getInt("ListPosition"), rs.getInt("ResponseTime"), rs
		// .getInt("CorrectionTrial"), rs.getInt("xpos"), rs.getInt("ypos")));
		// }
		// rs.close();
		// res.close();

		// res = results.createStatement();
		// res.execute("Select * from StimResponses WHERE correctionTrial > 0 ORDER BY TrialNum ASC, CorrectionTrial ASC");
		// rs = res.getResultSet();
		// while (rs.next()) {
		// corrTrialResponses.get(rs.getInt("TrialNum") - 1).add(
		// new SampleResponse(rs.getInt("ListPosition"), rs.getInt("ResponseTime"), rs
		// .getInt("CorrectionTrial"), rs.getInt("xpos"), rs.getInt("ypos")));
		// }
		// rs.close();
		// res.close();

		res = results.createStatement();
		res.execute("Select * from trialResults");
		rs = res.getResultSet();
		while (rs.next()) {
			Trial t = new Trial();
			t.trialNumber = rs.getInt("TrialNum");
			t.trialType = rs.getString("TrialType");
			t.configuration = rs.getString("Configuration");

			t.sampleSetSize = sampleSetSizes.get(t.trialNumber);
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

			t.sampleResponses = new Vector<SampleResponse>();
			t.correctionTrialSampleResponses = new Vector<SampleResponse>();

			t.actualViewTime = t.viewTime;

			for (int i = 1; i <= t.sampleSetSize; i++) {
				t.sampleStimuli.addAll(stims.get(t.trialNumber));
			}

			// changed item
			t.choiceStimuli.add(changedItems.get(t.trialNumber));

			// unchanged item
			t.choiceStimuli.add(stims.get(t.trialNumber).get(1));

			houHumanCDSession.trials.add(t);
		}

		rs.close();
		res.close();

		ConnectionCloser.shutIt(session);
		ConnectionCloser.shutIt(results);

		return houHumanCDSession;
	}

	@Override
	public Session fromXML(File xmlFile) throws SAXException, IOException,
			ParserConfigurationException
	{
		return new HoustonHumanCDSession(xmlFile);
	}

	@Override
	public Session fromStream(InputStream stream) throws FileNotFoundException, SAXException,
			IOException, ParserConfigurationException
	{
		return new HoustonHumanCDSession(stream);
	}

}

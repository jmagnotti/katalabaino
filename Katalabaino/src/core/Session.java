package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class Session implements Comparable<Session>
{
	public String			subject, id, sessionFile, resultsFile, comment;

	public boolean			isIncorrectCorrectionsEnabled;

	public Vector<Trial>	trials;
	
	public Session()
	{
		trials = new Vector<Trial>();
	}

	protected Session(InputStream is) throws ParserConfigurationException, SAXException,
			IOException
	{
		trials = new Vector<Trial>();

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document xmlFile = docBuilder.parse(is);

		xmlFile.getDocumentElement().normalize();

		Element sessionNode = xmlFile.getDocumentElement();

		NodeList trialNodes = xmlFile.getElementsByTagName("Trial");

		id = sessionNode.getAttribute("id");
		comment = sessionNode.getAttribute("comment");

		resultsFile = sessionNode.getAttribute("resultsFile");
		sessionFile = sessionNode.getAttribute("sessionFile");
		subject = sessionNode.getAttribute("subject");

		isIncorrectCorrectionsEnabled = Boolean.parseBoolean(sessionNode
				.getAttribute("incorrectCorrectionsEnabled"));

		Trial trial = null;
		for (int i = 0; i < trialNodes.getLength(); i++) {
			Element tNode = (Element) trialNodes.item(i);

			trial = new Trial();

			trial.trialType = tNode.getAttribute("trialType");
			trial.configuration = tNode.getAttribute("configuration");
			trial.correctResponse = tNode.getAttribute("correctResponse");

			trial.correctLocation = Integer.parseInt(tNode.getAttribute("correctLocation"));

			trial.response = tNode.getAttribute("response");

			trial.sampleSetSize = Integer.parseInt(tNode.getAttribute("sampleSetSize"));
			trial.choiceSetSize = Integer.parseInt(tNode.getAttribute("choiceSetSize"));

			trial.trialNumber = Integer.parseInt(tNode.getAttribute("trialNumber"));
			trial.responseTime = Double.parseDouble(tNode.getAttribute("responseTime"));
			trial.probeDelay = Double.parseDouble(tNode.getAttribute("probeDelay"));
			trial.intertrialInterval = Double.parseDouble(tNode.getAttribute("intertrialInterval"));

			trial.actualViewTime = Integer.parseInt(tNode.getAttribute("actualViewTime"));

			trial.observingResponse = Integer.parseInt(tNode.getAttribute("observingResponse"));

			trial.incorrectCorrections = Integer.parseInt(tNode
					.getAttribute("incorrectCorrections"));
			trial.viewTime = Integer.parseInt(tNode.getAttribute("viewTime"));

			trial.isTransfer = (1 == Integer.parseInt(tNode.getAttribute("isTransfer")));

			// sample responses
			NodeList sampleResponses = ((Element) (tNode.getElementsByTagName("SampleResponses")
					.item(0))).getElementsByTagName("SampleResponse");
			int lp, xp, yp, ct, vta;
			double rt;
			for (int j = 0; j < sampleResponses.getLength(); j++) {

				Element el = (Element) sampleResponses.item(j);

				lp = Integer.parseInt(el.getAttribute("position"));
				rt = Double.parseDouble(el.getAttribute("responseTime"));
				xp = Integer.parseInt(el.getAttribute("xpos"));
				yp = Integer.parseInt(el.getAttribute("ypos"));
				ct = Integer.parseInt(el.getAttribute("correctionTrial"));
				vta = Integer.parseInt(el.getAttribute("viewTimeAbort"));

				SampleResponse s = new SampleResponse(lp, rt, ct, vta, xp, yp);

				trial.sampleResponses.add(s);
			}

			// correction trial sample responses
			NodeList correctionTrialSampleResponses = ((tNode
					.getElementsByTagName("CorretionTrialSampleResponses")));

			if (correctionTrialSampleResponses.getLength() > 0) {

				correctionTrialSampleResponses = ((Element) correctionTrialSampleResponses.item(0))
						.getElementsByTagName("SampleResponse");

				for (int j = 0; j < correctionTrialSampleResponses.getLength(); j++) {
					Element el = (Element) sampleResponses.item(j);

					SampleResponse s = new SampleResponse(Integer.parseInt(el
							.getAttribute("position")), Double.parseDouble(el
							.getAttribute("responseTime")), Integer.parseInt(el
							.getAttribute("xpos")), Integer.parseInt(el.getAttribute("ypos")),
							Integer.parseInt(el.getAttribute("correctionTrial")));
					trial.correctionTrialSampleResponses.add(s);
				}
			}
			// now we need to get the stimuli
			// sample stimuli
			NodeList sampleStimuli = ((Element) (tNode.getElementsByTagName("SampleStimuli")
					.item(0))).getElementsByTagName("SampleStimulus");
			for (int j = 0; j < sampleStimuli.getLength(); j++) {
				Element el = (Element) sampleStimuli.item(j);

				Stimulus s = new Stimulus(el.getAttribute("file"), Integer.parseInt(el
						.getAttribute("position")), Integer.parseInt(el.getAttribute("colorID")),
						Integer.parseInt(el.getAttribute("shapeID")), el.getAttribute("label"));

				trial.sampleStimuli.add(s);
			}

			// choice stimuli
			NodeList choiceStimuli = ((Element) (tNode.getElementsByTagName("ChoiceStimuli")
					.item(0))).getElementsByTagName("ChoiceStimulus");
			for (int j = 0; j < choiceStimuli.getLength(); j++) {
				Element el = (Element) choiceStimuli.item(j);

				Stimulus s = new Stimulus(el.getAttribute("file"), Integer.parseInt(el
						.getAttribute("position")), Integer.parseInt(el.getAttribute("colorID")),
						Integer.parseInt(el.getAttribute("shapeID")), el.getAttribute("label"));

				trial.choiceStimuli.add(s);
			}

			trials.add(trial);
		}

		is.close();
	}

	public abstract Session fromMDB(Connection session, Connection results, String sName,
			String rName) throws SQLException;

	public abstract Session fromXML(File xmlFile) throws FileNotFoundException, SAXException,
			IOException, ParserConfigurationException;

	public abstract Session fromStream(InputStream stream) throws FileNotFoundException,
			SAXException, IOException, ParserConfigurationException;

	public void toXML(File outFile) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
		bw.write("<Session");
		bw.write(" trialCount='" + trials.size() + "' subject='" + subject + "' id='" + id
				+ "' sessionFile='" + sessionFile + "' resultsFile='" + resultsFile
				+ "' commment='" + comment + "' incorrectCorrectionsEnabled='"
				+ isIncorrectCorrectionsEnabled + "'");
		bw.write(">\n");

		for (Trial t : trials) {
			bw.write("\t<Trial trialNumber='" + t.trialNumber + "' trialType='" + t.trialType
					+ "' ");
			bw.write("configuration='" + t.configuration + "' correctResponse='"
					+ t.correctResponse + "' response='" + t.response + "' responseTime='"
					+ t.responseTime + "' probeDelay='" + t.probeDelay + "' viewTime='"
					+ t.viewTime + "' actualViewTime='" + t.actualViewTime
					+ "' intertrialInterval='" + t.intertrialInterval + "' isTransfer='"
					+ t.getTransferAsInt() + "' incorrectCorrections='" + t.incorrectCorrections
					+ "' observingResponse='" + t.observingResponse + "' sampleSetSize='"
					+ t.sampleSetSize + "' choiceSetSize='" + t.choiceSetSize
					+ "' responseLocation='" + t.responseLocation + "' correctLocation='"
					+ t.correctLocation + "'");
			bw.write(">\n");

			bw.write("\t\t<SampleResponses count='" + t.sampleResponses.size() + "'>\n");
			for (SampleResponse s : t.sampleResponses) {
				bw.write("\t\t\t<SampleResponse responseTime='" + s.responseTime + "' position='"
						+ s.position + "' correctionTrial='" + s.correctionTrial
						+ "' viewTimeAbort='" + s.viewTimeAbort + "' xpos='" + s.xpos + "' ypos='"
						+ s.ypos + "' />\n");
			}
			bw.write("\t\t</SampleResponses>\n");

			bw.write("\t\t<CorrectionTrialSampleResponses count='"
					+ t.correctionTrialSampleResponses.size() + "'>\n");
			for (SampleResponse s : t.correctionTrialSampleResponses) {
				bw.write("\t\t\t<SampleResponse responseTime='" + s.responseTime + "' position='"
						+ s.position + "' correctionTrial='" + s.correctionTrial
						+ "' viewTimeAbort='" + s.viewTimeAbort + "' xpos='" + s.xpos + "' ypos='"
						+ s.ypos + "' />\n");
			}
			bw.write("\t\t</CorrectionTrialSampleResponses>\n");

			bw.write("\t\t<SampleStimuli count='" + t.sampleStimuli.size() + "'>\n");
			for (Stimulus s : t.sampleStimuli) {
				bw.write("\t\t\t<SampleStimulus file='" + s.file + "' position='" + s.position
						+ "' label='" + s.label + "' colorID='" + s.colorID + "' shapeID='"
						+ s.shapeID + "'/>\n");
			}
			bw.write("\t\t</SampleStimuli>\n");

			bw.write("\t\t<ChoiceStimuli count='" + t.choiceStimuli.size() + "'>\n");
			for (Stimulus s : t.choiceStimuli) {
				bw.write("\t\t\t<ChoiceStimulus file='" + s.file + "' position='" + s.position
						+ "' label='" + s.label + "' colorID='" + s.colorID + "' shapeID='"
						+ s.shapeID + "'/>\n");
			}
			bw.write("\t\t</ChoiceStimuli>\n");
			bw.write("\t</Trial>\n");
		}

		bw.write("</Session>\n");
		bw.flush();
		bw.close();
	}

	public String getNameFromResultsFile(String rName)
	{
		return rName.split("[^A-Za-z]")[0];
	}

	protected void applySessionInfo(Session session, Connection results, String sName, String rName)
			throws SQLException
	{
		session.sessionFile = sName;
		session.resultsFile = rName;

		session.subject = getNameFromResultsFile(rName);

		session.id = rName.substring(session.subject.length(), rName.length() - 4);

		// handle some underscore naming conventions
		if (session.id.startsWith("_")) {
			session.id = session.id.substring(1);
			session.id = session.id.replace('_', '.').replace('w', '0');
		}
		else if (session.id.contains("_")) {
			session.id = session.id.substring(session.id.indexOf("_") + 2);
		}

		Statement ses = results.createStatement();
		ses.execute("Select IncorrectCorrectionEnabled from SessionResults");
		ResultSet rs = ses.getResultSet();

		while (rs.next()) {
			session.isIncorrectCorrectionsEnabled = rs.getBoolean("IncorrectCorrectionEnabled");
		}

		rs.close();
		ses.close();
	}
	
	
	@Override
	public int compareTo(Session o)
	{
		int retVal = subject.toLowerCase().compareTo(o.subject.toLowerCase());
		//
		if (retVal == 0) {
			if (Double.parseDouble(o.id) == Double.parseDouble(id))
				retVal = 0;
			else
				retVal = Double.parseDouble(id) > Double.parseDouble(o.id) ? 1 : -1;
		}

		// quick kludge
		// if (retVal == 0) retVal = resultsFile.compareTo(o.resultsFile);

		return retVal;
	}

	public int idAsInt()
	{
		return Integer.parseInt(id);
	}

}

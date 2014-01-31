package legacy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import core.Colors;
import core.Shapes;
import core.Stimulus;

public class SessionExtracter {
	// this isn't the cleanest thing ever, but the group is embedded in the
	// filename, so we need
	// this. Should probably store the group_id inside the XML for better
	// portability
	private static String currentFileName;

	/**
	 * Returns created session files given a file name
	 * 
	 * @param string
	 *            Path to file
	 * @return Constructed vector of sessions
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public static Vector<LegacySession> GetSessions(String path)
			throws IOException, SAXException, ParserConfigurationException {
		if (path.endsWith("dbo"))
			return GetSessionsFromDBO(new ZipFile(path));
		else if (path.endsWith("xml"))
			return GetSessionFromXML(new File(path));

		throw new IOException("Incorrect file format: " + path);
	}

	private static Vector<LegacySession> GetSessionFromXML(File file)
			throws SAXException, IOException, ParserConfigurationException {
		Vector<LegacySession> data = new Vector<LegacySession>();
		data.add(ExtractFromXML(new FileInputStream(file)));

		Collections.sort(data);

		return data;
	}

	private static Vector<LegacySession> GetSessionsFromDBO(ZipFile zipFile)
			throws IOException, SAXException, ParserConfigurationException {
		Vector<LegacySession> sessions = new Vector<LegacySession>();
		Enumeration<? extends ZipEntry> e = zipFile.entries();
		while (e.hasMoreElements()) {
			ZipEntry ze = e.nextElement();
			currentFileName = ze.getName();
			InputStream is = zipFile.getInputStream(ze);
			sessions.add(ExtractFromXML(is));
		}

		Collections.sort(sessions);

		return sessions;
	}

	/**
	 * 
	 * @param is
	 *            An XML input stream
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private static LegacySession ExtractFromXML(InputStream is)
			throws SAXException, IOException, ParserConfigurationException {
		LegacySession session = new LegacySession();
		System.out.println(currentFileName);
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document xmlFile = docBuilder.parse(is);

		xmlFile.getDocumentElement().normalize();

		Element sessionNode = xmlFile.getDocumentElement();

		NodeList trialNodes = xmlFile.getElementsByTagName("Trial");
		session.id = sessionNode.getAttribute("id");
		session.reportingMethod = Integer.parseInt(sessionNode
				.getAttribute("reportingMethod"));
		session.label = sessionNode.getAttribute("label");

		Vector<Trial> trials = new Vector<Trial>();
		int trialNumber = -1;
		for (int i = 0; i < trialNodes.getLength(); i++) {
			Element trialNode = (Element) trialNodes.item(i);
			int oldTNum = trialNumber;
			trialNumber = Integer.parseInt(trialNode
					.getAttribute("trialNumber"));

			if (oldTNum == trialNumber)
				System.err.println("OH NO @ " + trialNumber);

			int trialType = Integer.parseInt(trialNode
					.getAttribute("trialType"));
			int choiceLocation = Integer.parseInt(trialNode
					.getAttribute("choiceLocation"));
			int responseTime = Integer.parseInt(trialNode
					.getAttribute("responseTime"));
			int sampleDisplaySize = Integer.parseInt(trialNode
					.getAttribute("sampleDisplaySize"));
			int choiceDisplaySize = Integer.parseInt(trialNode
					.getAttribute("choiceDisplaySize"));

			int iti = Integer.parseInt(trialNode
					.getAttribute("interTrialInterval"));

			double viewTime = Integer.parseInt(trialNode
					.getAttribute("sampleDisplayDuration"));
			double probeDelay = Integer.parseInt(trialNode
					.getAttribute("retentionInterval"));

			NodeList sampleStimuli = ((Element) (trialNode
					.getElementsByTagName("sampleStimuli").item(0)))
					.getElementsByTagName("stimulus");
			Vector<Stimulus> sStimuli = new Vector<Stimulus>();
			int start, stop;
			for (int j = 0; j < sampleStimuli.getLength(); j++) {
				Stimulus s = new Stimulus();
				s.file = ((Element) sampleStimuli.item(j))
						.getAttribute("imageFile");
				s.position = Integer.parseInt(((Element) sampleStimuli.item(j))
						.getAttribute("location"));

				start = s.file.lastIndexOf("/");
				stop = s.file.lastIndexOf(".");
				s.file = s.file.substring(start + 1, stop);

				// System.out.println(s.file);

				s.colorID = getColorIDFromFileName(s.file);

				s.shapeID = Shapes.RECTANGLE;

				s.file = s.file + ".jpg";

				s.label = Colors.GetInstance().colorIDToLabel.get(s.colorID)
						+ ":"
						+ Shapes.GetInstance().shapeIDToLabel.get(s.shapeID);

				sStimuli.add(s);
			}

			NodeList choiceStimuli = ((Element) (trialNode
					.getElementsByTagName("choiceStimuli").item(0)))
					.getElementsByTagName("stimulus");

			Vector<Stimulus> cStimuli = new Vector<Stimulus>();
			for (int j = 0; j < choiceStimuli.getLength(); j++) {
				Stimulus s = new Stimulus();
				s.file = ((Element) choiceStimuli.item(j))
						.getAttribute("imageFile");
				s.position = Integer.parseInt(((Element) choiceStimuli.item(j))
						.getAttribute("location"));
				start = s.file.lastIndexOf("/");
				stop = s.file.lastIndexOf(".");
				s.file = s.file.substring(start + 1, stop);

				s.colorID = getColorIDFromFileName(s.file);

				s.file = s.file + ".jpg";

				s.shapeID = Shapes.RECTANGLE;
				s.label = Colors.GetInstance().colorIDToLabel.get(s.colorID)
						+ ":"
						+ Shapes.GetInstance().shapeIDToLabel.get(s.shapeID);

				// System.out.println(s.colorName);
				cStimuli.add(s);
			}

			// we need to mess with trialType just a little bit
			if (session.reportingMethod == LegacySession.FORCED_CHOICE) {
				trialType = Trial.FORCED_CHOICE_TRIAL;
			} else if (session.reportingMethod == LegacySession.NC_FORCED_CHOICE) {
				trialType = Trial.NC_FORCED_CHOICE_TRIAL;
			}
			if (session.reportingMethod == LegacySession.SAME_DIFFERENT) {
				trialType = trialType == Trial.CHANGE_TRIAL ? Trial.DIFFERENT_TRIAL
						: Trial.SAME_TRIAL;

				choiceLocation = choiceLocation == Trial.CHANGE_RESPONSE ? Trial.SAME_RESPONSE
						: Trial.DIFFERENT_RESPONSE;
			}

			// ###I don't think this works correctly
			// else if (session.reportingMethod == Session.CHOOSE_SAME_LINEUP) {
			// trialType = Trial.CHOOSE_SAME_LINEUP;
			//
			// // we need to change all the stimulus locations, because we did
			// lineup, not random
			// Vector<Integer> positions =
			// Session.GetLineUpPositions(cStimuli.size());
			// for (int c = 0; c < positions.size(); c++) {
			// cStimuli.get(c).location = positions.get(c);
			// // System.out.println("Pos: " + cStimuli.get(c).location);
			// }
			// }

			// System.out.println("Building: " + currentFileName + ", t: " +
			// trialNumber);

			// System.out.println("Choice Loc: " + choiceLocation);

			Trial trial = new Trial(trialNumber, trialType, choiceLocation,
					responseTime, sampleDisplaySize, choiceDisplaySize,
					(int) probeDelay, (int) viewTime, iti, sStimuli, cStimuli);
			trials.add(trial);
		}

		session.group = currentFileName.indexOf("A") > 0 ? LegacySession.GROUP_A
				: LegacySession.GROUP_B;
		session.fileName = currentFileName;
		session.setTrials(trials);

		return session;
	}

	private static int getColorIDFromFileName(String file) {

		int colorID = -1;

		if (file.equals("yellowGreen"))
			colorID = Colors.YELLOWGREEN;
		else if (file.equals("green"))
			colorID = Colors.GREEN;
		else if (file.startsWith("sn"))
			colorID = Colors.SNAKE;
		else if (file.startsWith("k"))
			colorID = Colors.KSCOPE;
		else if (file.startsWith("sp"))
			colorID = Colors.SPIDER;
		else
			colorID = Colors.GetInstance().fileToColorID(file);

		return colorID;
	}
}

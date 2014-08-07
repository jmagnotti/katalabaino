package legacy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import core.trial.Stimulus;

public class ReformatAUHumanCD {

	public static final String dir = "/Users/jmagnotti/Dropbox/CD Spider Project/Data Files/";

	// .GetSessions("Y:/Desktop/dissertation_files/cd_data/pick-change.dbo");

	public static void main(String[] args) throws Exception {
		Vector<LegacySession> sessions = SessionExtracter.GetSessions(dir
				+ "phobia_xml.dbo");
		PrintSessions(sessions);
	}

	public static void PrintSessions(Vector<LegacySession> sessions) {
		for (LegacySession s : sessions) {
			String fname = s.fileName.substring(0, s.fileName.length() - 3);
			File xmlOut = new File(dir + fname + "kml");

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(xmlOut));
				bw.write("<Session");
				bw.write(" trialCount='" + s.getTrials().size() + "' subject='"
						+ s.label + "' id='" + s.id + "' sessionFile='"
						+ s.fileName + "' resultsFile='" + s.fileName
						+ "' commment='" + s.label
						+ "' incorrectCorrectionsEnabled='false'");
				bw.write(">\n");
				for (Trial t : s.getTrials()) {

					if (t.choicePosition == 0)
						System.err.println("Caught on : " + t.trialNumber);

					bw.write("\t<Trial trialNumber='" + t.trialNumber
							+ "' trialType='" + t.trialType + "' ");
					bw.write("configuration='"
							+ (t.sampleDisplaySize + "_" + t.choiceDisplaySize)
							+ "' correctResponse='"
							+ t.correctColor
							+ "' response='"
							+ t.choiceColor
							+ "' responseTime='"
							+ t.responseTime
							+ "' probeDelay='"
							+ t.probeDelay
							+ "' viewTime='"
							+ t.viewTime
							+ "' actualViewTime='"
							+ t.viewTime
							+ "' intertrialInterval='"
							+ t.interTrialInterval
							+ "' isTransfer='0' incorrectCorrections='0' observingResponse='0' sampleSetSize='"
							+ t.sampleDisplaySize + "' choiceSetSize='"
							+ t.choiceDisplaySize + "' responseLocation='"
							+ t.choicePosition + "' correctLocation='"
							+ t.correctPosition + "'");
					bw.write(">\n");

					bw.write("\t\t<SampleResponses count='0'>\n");
					bw.write("\t\t</SampleResponses>\n");

					bw.write("\t\t<CorrectionTrialSampleResponses count='0'>\n");
					bw.write("\t\t</CorrectionTrialSampleResponses>\n");

					bw.write("\t\t<SampleStimuli count='"
							+ t.sampleDisplayStimuli.size() + "'>\n");
					for (Stimulus stim : t.sampleDisplayStimuli) {
						bw.write("\t\t\t<SampleStimulus file='" + stim.file
								+ "' position='" + stim.position + "' label='"
								+ stim.label + "' colorID='" + stim.colorID
								+ "' shapeID='" + stim.shapeID + "'/>\n");
					}
					bw.write("\t\t</SampleStimuli>\n");

					bw.write("\t\t<ChoiceStimuli count='"
							+ t.choiceDisplayStimuli.size() + "'>\n");
					for (Stimulus stim : t.choiceDisplayStimuli) {
						bw.write("\t\t\t<ChoiceStimulus file='" + stim.file
								+ "' position='" + stim.position + "' label='"
								+ stim.label + "' colorID='" + stim.colorID
								+ "' shapeID='" + stim.shapeID + "'/>\n");
					}
					bw.write("\t\t</ChoiceStimuli>\n");
					bw.write("\t</Trial>\n");
				}
				bw.write("</Session>\n");
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

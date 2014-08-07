package analyses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import core.trial.Colors;
import core.trial.Shapes;

public class CountingStroopQuick
{
	public static String	newline			= System.getProperty("line.separator");

	public static String[]	trialStimuli	= { "ani.03.02.png", "ani.04.04.png", "ani.04.03.png",
			"ani.02.02.png", "ani.01.04.png", "ani.03.04.png", "ani.04.01.png", "ani.04.03.png",
			"ani.03.02.png", "ani.03.03.png", "ani.02.01.png", "ani.04.01.png", "ani.03.01.png",
			"ani.02.04.png", "ani.03.04.png", "ani.01.01.png", "ani.01.03.png", "ani.02.03.png",
			"ani.03.04.png", "ani.02.01.png", "num.04.03.png", "num.01.03.png", "num.03.01.png",
			"num.04.01.png", "num.01.04.png", "num.02.03.png", "num.03.01.png", "num.01.04.png",
			"num.04.03.png", "num.01.03.png", "num.03.01.png", "num.02.04.png", "num.02.01.png",
			"num.01.02.png", "num.04.03.png", "num.03.04.png", "num.03.02.png", "num.01.02.png",
			"num.04.01.png", "num.02.04.png", "neg.02.02.png", "neg.01.02.png", "neg.03.03.png",
			"neg.01.04.png", "neg.04.04.png", "neg.03.01.png", "neg.04.02.png", "neg.02.03.png",
			"neg.02.01.png", "neg.03.04.png", "neg.03.02.png", "neg.01.01.png", "neg.03.04.png",
			"neg.04.03.png", "neg.04.01.png", "neg.01.03.png", "neg.01.02.png", "neg.03.03.png",
			"neg.02.02.png", "neg.01.04.png", "num.04.03.png", "num.01.04.png", "num.02.03.png",
			"num.04.02.png", "num.01.04.png", "num.03.04.png", "num.01.02.png", "num.02.04.png",
			"num.04.01.png", "num.03.01.png", "num.01.04.png", "num.03.04.png", "num.04.02.png",
			"num.02.03.png", "num.04.02.png", "num.01.03.png", "num.02.01.png", "num.03.04.png",
			"num.04.02.png", "num.02.04.png", "neg.04.03.png", "neg.01.04.png", "neg.03.02.png",
			"neg.04.04.png", "neg.02.02.png", "neg.04.01.png", "neg.01.02.png", "neg.04.02.png",
			"neg.03.01.png", "neg.02.03.png", "neg.01.03.png", "neg.02.04.png", "neg.04.04.png",
			"neg.02.01.png", "neg.03.03.png", "neg.01.01.png", "neg.04.01.png", "neg.04.03.png",
			"neg.01.02.png", "neg.04.02.png", "ani.03.04.png", "ani.03.02.png", "ani.04.04.png",
			"ani.01.01.png", "ani.03.02.png", "ani.02.03.png", "ani.02.01.png", "ani.01.04.png",
			"ani.03.04.png", "ani.01.03.png", "ani.02.04.png", "ani.04.04.png", "ani.03.03.png",
			"ani.03.01.png", "ani.02.02.png", "ani.04.02.png", "ani.01.02.png", "ani.04.01.png",
			"ani.02.01.png", "ani.02.04.png", "neg.01.04.png", "neg.03.01.png", "neg.02.02.png",
			"neg.01.02.png", "neg.04.02.png", "neg.02.01.png", "neg.03.02.png", "neg.04.04.png",
			"neg.01.03.png", "neg.02.04.png", "neg.03.04.png", "neg.03.03.png", "neg.04.03.png",
			"neg.01.01.png", "neg.02.03.png", "neg.03.01.png", "neg.03.03.png", "neg.04.03.png",
			"neg.02.02.png", "neg.01.02.png", "ani.04.03.png", "ani.02.03.png", "ani.02.01.png",
			"ani.04.01.png", "ani.03.02.png", "ani.01.02.png", "ani.03.01.png", "ani.02.02.png",
			"ani.01.03.png", "ani.01.01.png", "ani.02.04.png", "ani.03.03.png", "ani.04.04.png",
			"ani.03.01.png", "ani.01.03.png", "ani.01.01.png", "ani.03.04.png", "ani.04.02.png",
			"ani.02.01.png", "ani.04.01.png", "num.03.01.png", "num.01.02.png", "num.03.04.png",
			"num.04.01.png", "num.03.02.png", "num.02.03.png", "num.04.03.png", "num.04.01.png",
			"num.01.03.png", "num.01.04.png", "num.04.02.png", "num.02.01.png", "num.03.02.png",
			"num.01.02.png", "num.03.04.png", "num.01.02.png", "num.03.04.png", "num.04.01.png",
			"num.01.03.png", "num.01.04.png" };

	public static void main(String[] args) throws Exception
	{

		String name = "stroop008-32";

		for (String subj : new String[] { "hamrice" }) {
			for (String id : new String[] { "001", "002", "003", "004" }) {

				BufferedReader br = new BufferedReader(new FileReader(new File(
						"/Users/jmagnotti/warehouse/cstroop_fmri/datafiles/" + subj + "." + id)));

				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
						"/Users/jmagnotti/warehouse/cstroop_fmri/datafiles/" + name + "." + id
								+ ".kml")));

				bw.write("<Session trialCount='180' subject='" + name + "' id='" + id
						+ "' resultsFile='" + subj + "." + id + "' sessionFile='" + subj + "." + id
						+ "' comment='Human fMRI cStroop' incorrectCorrectionsEnabled='false'>"
						+ newline);

				int trialNumber = -1, choice = -1, reactionTime = -1, previousTrialNumber = -1;
				int colorID = Colors.COUNTING_STROOP;
				int shapeID = Shapes.COUTING_STROOP;
				while (br.ready()) {

					String l = br.readLine();

					String[] vals = l.split(",");

					trialNumber = Integer.parseInt(vals[0]) + 1;

					if (trialNumber > previousTrialNumber) {

						choice = Integer.parseInt(vals[1]) - 48;

						// System.out.println(trialNumber);

						reactionTime = Integer.parseInt(vals[2]);

						String[] stimAttr = trialStimuli[trialNumber - 1].split("\\.");

						String label = stimAttr[0] + ":C-Stroop";

						String ttype = stimAttr[0];
						int config = Integer.parseInt(stimAttr[2]);

						int corrR, corrLoc;
						corrR = corrLoc = Integer.parseInt(stimAttr[2]);

						bw.write("\t<Trial trialNumber='"
								+ trialNumber
								+ "' trialType='"
								+ ttype
								+ "' configuration='"
								+ config
								+ "' correctResponse='"
								+ corrR
								+ "' response='"
								+ choice
								+ "' responseTime='"
								+ reactionTime
								+ "' probeDelay='0' viewTime='1500' actualViewTime='1500' intertrialInterval='0' isTransfer='0' incorrectCorrections='0' observingResponse='0' sampleSetSize='0' choiceSetSize='"
								+ config + "' responseLocation='" + choice + "' correctLocation='"
								+ corrLoc + "' >" + newline);

						bw.write("\t\t<SampleResponses count='0'>" + newline);
						bw.write("\t\t</SampleResponses>" + newline);
						bw.write("\t\t<CorrectionTrialSampleResponses count='0'>" + newline);
						bw.write("\t\t</CorrectionTrialSampleResponses>" + newline);
						bw.write("\t\t<SampleStimuli count='0'>" + newline);
						bw.write("\t\t</SampleStimuli>" + newline);
						bw.write("\t\t<ChoiceStimuli count='1'>" + newline);
						bw.write("\t\t\t<ChoiceStimulus file='" + trialStimuli[trialNumber - 1]
								+ "' position='0' labels='" + label + "' colorID='" + colorID
								+ "' shapeID='" + shapeID + "' />" + newline);
						bw.write("\t\t</ChoiceStimuli>" + newline);
						bw.write("\t</Trial>" + newline);
					}

					previousTrialNumber = trialNumber;
				}
				br.close();
				bw.write("</Session>" + newline);
				bw.flush();
				bw.close();
			}
		}
	}
}

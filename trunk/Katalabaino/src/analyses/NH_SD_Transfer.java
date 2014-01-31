package analyses;

import java.io.File;
import java.util.Vector;

import mappers.MeanResponseTimeMap;
import mappers.MeanSampleCompletionResponseTimeMap;
import mappers.MeanSampleResponseMap;
import mappers.PercentCorrectMap;
import mappers.SessionInformationMap;
import sessions.SDSession;
import splitters.BaselineTransferSplitter;
import splitters.TrialTypeSplitter;
import core.CombinedAnalysis;
import core.ComparisonRule;
import core.FileTypeConverter;
import core.Session;
import core.SessionFactory;
import filters.ResponseTimeFilter;
import filters.SampleResponseResponseTimeFilter;

public class NH_SD_Transfer implements Runnable {
	private String dir, bird;

	public NH_SD_Transfer(String dir, String bird) {
		this.dir = dir;
		this.bird = bird;
	}

	@Override
	public void run() {
		String workDir = dir + bird + "/";

		try {
			File zipFile = FileTypeConverter.CreateZipFileFromDirectory(
					workDir, bird, new SDSession());
			Vector<Session> sessions = SessionFactory.BuildSessions(
					new SDSession(), zipFile);

			CombinedAnalysis ca = new CombinedAnalysis(sessions, workDir + bird
					+ "_transfer.csv");

			ca.addMap(new SessionInformationMap());
			ca.addMap(new PercentCorrectMap());
			ca.addSplitter(new BaselineTransferSplitter());
			ca.analyze();

			ca.addSplitter(new TrialTypeSplitter(true));
			ca.analyze();

			ca.clearMaps();
			ca.addMap(new SessionInformationMap());
			ca.addMap(new MeanResponseTimeMap());
			ca.addFilter(new ResponseTimeFilter(new ComparisonRule(
					ComparisonRule.LT_OR_EQ, 10 * 1000)));

			ca.addSplitter(new BaselineTransferSplitter());
			ca.analyze(true);

			ca.addMap(new SessionInformationMap());
			ca.addMap(new MeanSampleCompletionResponseTimeMap());
			ca.addFilter(new SampleResponseResponseTimeFilter(1));
			ca.addSplitter(new BaselineTransferSplitter());
			ca.analyze(true);

			ca.addMap(new SessionInformationMap());
			ca.addMap(new MeanSampleResponseMap());
			ca.addSplitter(new BaselineTransferSplitter());
			ca.analyze();

			// the true tells the accumulator to add an empty column between
			// each analysis
			ca.accumulate(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		String dir = "Z:/warehouse/magpie/";
		// String[] birds = { "blue", "blueA0", "blueA3", "red", "red004",
		// "redx01", "yellow" };
		String[] birds = { "red004" };

		for (int i = 0; i < birds.length; i++) {
			NH_SD_Transfer nhsda = new NH_SD_Transfer(dir, birds[i]);
			Thread t = new Thread(nhsda);
			t.start();
		}

	}

}

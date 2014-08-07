package analyses;

import java.io.File;
import java.util.Vector;

import mappers.PercentCorrectMap;
import mappers.SessionInformationMap;
import sessions.YN_CDSession;
import splitters.BlockSplitter;
import splitters.SampleSetSizeSplitter;
import core.analysis.Analysis;
import core.session.Session;
import core.session.SessionFactory;
import file.FileTypeConverter;

public class YN_CD_HUMAnalysis {
	public static void main(String[] args) throws Exception {
		// String dir = "Z:/warehouse/YN_cd/acquisition/";
		// String dir = "/Users/jmagnotti/warehouse/YN_cd/acquisition/";
		String dir = "Z:/Dropbox/AU_Projects/katz/YN CD Human/Results/";
		String bird = "pilot";
		String workDir = dir + bird + "/";

		FileTypeConverter.CreateZipFileFromDirectory(workDir, bird, new YN_CDSession());

		File zipFile = new File(workDir + bird + ".dbo");
		// File zipFile = new File(
		// "/Users/jmagnotti/Dropbox/CD Spider Project/Data Files/phobia157-164.dbo");

		Vector<Session> sessions = SessionFactory.BuildSessions(new YN_CDSession(), zipFile);

		Analysis analysis = new Analysis(sessions);

		analysis.addMap(new SessionInformationMap());
		analysis.addMap(new PercentCorrectMap());
		// analysis.addMap(new CountMap());
		// analysis.addMap(new CountCorrectMap());

		// analysis.addMap(new DPrimeMap());
		// analysis.addMap(new MedianResponseTimeMap());
		// analysis.addMap(new MeanSampleResponseMap());
		// analysis.addMap(new ObservingResponseInfoMap());

		// analysis.addSplitter(new YNPeckSplitter());

		// analysis.addSplitter(new SampleClassSplitter());
		analysis.addSplitter(new SampleSetSizeSplitter());
		// analysis.addSplitter(new TrialTypeSplitter());
		// analysis.addSplitter(new ProbeDelaySplitter());
		// analysis.addSplitter(new ConfigurationSplitter("icm"));
		// analysis.addSplitter(new YN_PeckDistanceSplitter());

		// analysis.addSplitter(new SampleLabelSplitter());
		// analysis.addSplitter(new ProbeLabelSplitter());

		analysis.addSplitter(new BlockSplitter(20, 180));

		analysis.setFieldDelimiter(",");
		analysis.setOutputLocation(new File("Z:/Dropbox/AU_Projects/katz/YN CD Human/yncd_acquisition.csv"));

		analysis.analyze();
	}

}

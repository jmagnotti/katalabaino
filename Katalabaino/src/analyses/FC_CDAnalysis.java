package analyses;

import java.io.File;
import java.util.Vector;

import mappers.SessionInformationMap;
import sessions.HoustonHumanCDSession;
import splitters.SampleSetSizeSplitter;
import testing.ImageMap;
import testing.MultiSessionHDAnalysis;
import core.Analysis;
import core.FileTypeConverter;
import core.Session;
import core.SessionFactory;

public class FC_CDAnalysis
{
	public static void main(String[] args) throws Exception
	{
		String dir = "Y:/warehouse/FC_cd/";
		String bird = "twain";
		String workDir = dir + bird + "/";

		// FileTypeConverter.CreateZipFileFromDirectory("Y:/warehouse/human_cd/caitlin/results/",
		// "subjects", new HoustonHumanCDSession());

		// System.exit(0);

		// File zipFile = new File(workDir + bird + ".dbo");
		File zipFile = new File("Y:/warehouse/human_cd/caitlin/results/subjects.dbo");
		Vector<Session> sessions = SessionFactory.BuildSessions(new HoustonHumanCDSession(),
				zipFile);

		Analysis analysis = new Analysis(sessions);

		analysis.addMap(new SessionInformationMap());
		// analysis.addMap(new MaxSampleSizeMap());
		// analysis.addSplitter(new SampleColorSplitter());
		// analysis.addMap(new AccuracyMap());

		// analysis.addMap(new ImageMap());

		// analysis.addMap(new CountMap());
		// analysis.addSplitter(new CD_ProbeDelaySplitter());

		// analysis.addFilter(new SessionIDFilter(new ComparisonRule(ComparisonRule.GT_OR_EQ,
		// sessions
		// .lastElement().idAsInt() - 30)));
		// analysis.addSplitter(new PeckNoPeckSplitter());

		// analysis.addMap(new SamplePecksAverageMap());
		analysis.addSplitter(new SampleSetSizeSplitter());

		// analysis.addMap(new MaxProbeDelayMap());
		// analysis.addFilter(new ProbeDelayFilter(100));

		// Filter notSS1 = new SampleSetSizeFilter(new ComparisonRule(ComparisonRule.GREATER_THAN,
		// 1));
		// analysis.addFilter(notSS1);

		// analysis.analyze();

		MultiSessionHDAnalysis mshda = new MultiSessionHDAnalysis(sessions);
		mshda.addMap(new ImageMap());
		// mshda.addMap(new ItemPairAccuracyMap());
		// mshda.addFilter(new SessionIDFilter(new ComparisonRule(ComparisonRule.GREATER_THAN,
		// sessions.lastElement().idAsInt() - 31)));
		// mshda.addFilter(new SampleSetSizeFilter(2));
		mshda.analyze();
	}
}

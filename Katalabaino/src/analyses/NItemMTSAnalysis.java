package analyses;

import java.io.File;
import java.util.Vector;

import mappers.AverageRTMap;
import mappers.SessionInfoMap;
import sessions.N_ItemMTSSession;
import splitters.ChoiceSetSizeSplitter;
import core.Analysis;
import core.FileTypeConverter;
import core.Session;
import core.SessionFactory;
import filters.ComparisonRule;
import filters.CorrectTrialsOnlyFilter;
import filters.ReactionTimeFilter;

public class NItemMTSAnalysis
{
	public static void main(String[] args) throws Exception
	{
		String dir = "Y:/warehouse/human_targetsearch/";
		String bird = "results";
		String workDir = dir + bird + "/";

		FileTypeConverter.CreateZipFileFromDirectory(workDir, bird, new N_ItemMTSSession());

		File zipFile = new File(workDir + bird + ".dbo");
		Vector<Session> sessions = SessionFactory.BuildSessions(new N_ItemMTSSession(), zipFile);

		Analysis analysis = new Analysis(sessions);

		analysis.addMap(new SessionInfoMap());
		// analysis.addMap(new AccuracyMap());
		// analysis.addMap(new CPInfoMap());
		analysis.addSplitter(new ChoiceSetSizeSplitter());

		// analysis.addSplitter(new CorrectIncorrectSplitter());

		// analysis.addSplitter(new CorrectPositionSplitter());

		analysis.addFilter(new ReactionTimeFilter(
				new ComparisonRule(ComparisonRule.LT_OR_EQ, 10000)));
		analysis.addFilter(new CorrectTrialsOnlyFilter());
		analysis.addMap(new AverageRTMap());

		// analysis.addFilter(new ChoiceSetSizeFilter(new
		// ComparisonRule(ComparisonRule.NOT_EQUAL_TO,
		// 1)));

		analysis.analyze();

		// MultiSessionHDAnalysis mshda = new MultiSessionHDAnalysis(sessions);
		// mshda.addMap(new AccuracyMap());
		// mshda.addSplitter(new CorrectPositionSplitter());

	}

}

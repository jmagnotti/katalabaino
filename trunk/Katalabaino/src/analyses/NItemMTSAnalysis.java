package analyses;

import java.io.File;
import java.util.Vector;

import mappers.MeanResponseTimeMap;
import mappers.SessionInformationMap;
import sessions.N_ItemMTSSession;
import splitters.ChoiceSetSizeSplitter;
import core.Analysis;
import core.ComparisonRule;
import core.FileTypeConverter;
import core.Session;
import core.SessionFactory;
import filters.CorrectTrialsOnlyFilter;
import filters.ResponseTimeFilter;

public class NItemMTSAnalysis
{
	public static void main(String[] args) throws Exception
	{
		String dir = "Y:/warehouse/n-item_mts/";
		String bird = "uninformed";
		String workDir = dir + bird + "/";

		// FileTypeConverter.CreateZipFileFromDirectory(workDir, bird, new N_ItemMTSSession());

//		File zipFile = new File(workDir + bird + ".dbo");
		File zipFile = new File("Y:/Downloads/relatestnitemmtsdata/Jupiter.dbo");
		Vector<Session> sessions = SessionFactory.BuildSessions(new N_ItemMTSSession(), zipFile);

		Analysis analysis = new Analysis(sessions);

		analysis.addMap(new SessionInformationMap());
		// analysis.addMap(new AccuracyMap());
		// analysis.addMap(new CPInfoMap());
		analysis.addSplitter(new ChoiceSetSizeSplitter());

		// analysis.addSplitter(new CorrectIncorrectSplitter());

		// analysis.addSplitter(new CorrectPositionSplitter());

		analysis.addFilter(new ResponseTimeFilter(
				new ComparisonRule(ComparisonRule.LT_OR_EQ, 10000)));
		analysis.addFilter(new CorrectTrialsOnlyFilter());
		analysis.addMap(new MeanResponseTimeMap());

		// analysis.addFilter(new ChoiceSetSizeFilter(new
		// ComparisonRule(ComparisonRule.NOT_EQUAL_TO,
		// 1)));

		analysis.analyze();

		// MultiSessionHDAnalysis mshda = new MultiSessionHDAnalysis(sessions);
		// mshda.addMap(new AccuracyMap());
		// mshda.addSplitter(new CorrectPositionSplitter());

	}

}

package analyses;

import java.io.File;
import java.util.Vector;

import mappers.*;
import sessions.SDSession;
import splitters.*;
import filters.*;
import core.*;

public class NutcrackerSDAnalysis
{
	private static Analysis	analysis;

	public static void main(String[] args) throws Exception
	{
		String dir = "Y:/warehouse/nutcracker/";
		String bird = "lance";
		String workDir = dir + bird + "/";

		// FileTypeConverter.CreateZipFileFromDirectory(workDir, bird, new SDSession());
		File zipFile = new File(workDir + bird + ".dbo");

		Vector<Session> sessions = SessionFactory.BuildSessions(new SDSession(), zipFile);

		analysis = new Analysis(sessions);
		analysis.addMap(new SessionInfoMap());
		// analysis.addSplitter(new BaselineTransferSplitter());

		analysis.addMap(new CountMap());
		Accuracy();
	}

	public static void ICMap()
	{
		analysis.addMap(new IncorrectCorrectionsMap());
		analysis.analyze();
	}

	public static void TTypeCorrect_RT()
	{
		analysis.addSplitter(new TrialTypeSplitter());
		analysis.addFilter(new CorrectTrialsOnlyFilter());
		analysis.addFilter(new ReactionTimeFilter(new ComparisonRule(ComparisonRule.LT_OR_EQ,
				10 * 1000)));
		analysis.addMap(new AverageRTMap());

		analysis.analyze();
	}

	public static void Accuracy()
	{
		analysis.addMap(new AccuracyMap());
		analysis.analyze();
	}

	public static void TTypeAccuracy()
	{
		analysis.addSplitter(new TrialTypeSplitter());
		Accuracy();
	}

	public static void TTypeTransferAccuracy()
	{
		analysis.addSplitter(new BaselineTransferSplitter());
		TTypeAccuracy();
	}

	public static void FR_RT()
	{
		analysis.addFilter(new FR_ReactionTimeFilter(FR_ReactionTimeFilter.FOUR_SIGMA));
		analysis.addMap(new AverageFRMap());
		analysis.addMap(new AverageFR_RTMap());
		analysis.analyze();
	}

}

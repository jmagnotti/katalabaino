package analyses;

import java.io.File;
import java.util.Vector;

import mappers.AccuracyMap;
import mappers.AverageRTMap;
import mappers.CountMap;
import mappers.SessionInfoMap;
import splitters.TrialTypeSplitter;
import core.Analysis;
import core.FileTypeConverter;
import core.KMLFilter;
import core.Session;
import core.SessionFactory;
import filters.ComparisonRule;
import filters.CorrectTrialsOnlyFilter;
import filters.ReactionTimeFilter;
import filters.SessionIDFilter;

public class CountingStroopAnalysis
{

	public static void vectorAdd(Vector<File> v, File... files)
	{
		for (File f : files) {
			v.add(f);
		}
	}

	public static void main(String args[]) throws Exception
	{
		String dir = "/Users/jmagnotti/warehouse/cstroop_fmri/datafiles/";
		String bird = "couting_stroopResults";
		String workDir = dir;

		// Vector<File> files = new Vector<File>();
		// File fDir = new File(workDir);
		// vectorAdd(files, fDir.listFiles(new KMLFilter()));
		// FileTypeConverter.ZipToDBO(workDir + bird + ".dbo", files, false);

		File zipFile = new File(workDir + bird + ".dbo");
		Vector<Session> sessions = SessionFactory.BuildSessions(new CountingStroopSession(),
				zipFile);

		Analysis analysis = new Analysis(sessions);

		analysis.addMap(new SessionInfoMap());
		analysis.addMap(new AccuracyMap());
		// analysis.addMap(new CountMap());
		// analysis.addSplitter(new ConfigurationSplitter("n"));
		analysis.addFilter(new ReactionTimeFilter(ReactionTimeFilter.FOUR_SIGMA));

		analysis.addFilter(new SessionIDFilter(new ComparisonRule(ComparisonRule.EQUAL_TO, 4)));

//		analysis.addFilter(new CorrectTrialsOnlyFilter());
		
		analysis.addSplitter(new TrialTypeSplitter());
		analysis.analyze();
	}
}

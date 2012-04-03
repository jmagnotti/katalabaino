package analyses;

import java.io.File;
import java.util.Vector;

import mappers.PercentCorrectMap;
import mappers.SessionInformationMap;
import splitters.TrialTypeSplitter;
import core.Analysis;
import core.ComparisonRule;
import core.Session;
import core.SessionFactory;
import filters.ResponseTimeFilter;
import filters.SessionNumberFilter;

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

		analysis.addMap(new SessionInformationMap());
		analysis.addMap(new PercentCorrectMap());
		// analysis.addMap(new CountMap());
		// analysis.addSplitter(new ConfigurationSplitter("n"));
		analysis.addFilter(new ResponseTimeFilter(ResponseTimeFilter.FOUR_SIGMA));

		analysis.addFilter(new SessionNumberFilter(new ComparisonRule(ComparisonRule.EQUAL_TO, 4)));

		// analysis.addFilter(new CorrectTrialsOnlyFilter());

		analysis.addSplitter(new TrialTypeSplitter());
		analysis.analyze();
	}
}

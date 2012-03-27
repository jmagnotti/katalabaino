package analyses;

import java.io.File;
import java.util.Vector;

import mappers.AccuracyMap;
import mappers.AverageRTMap;
import mappers.SessionInfoMap;
import sessions.StroopSession;
import splitters.ConfigurationSplitter;
import core.Analysis;
import core.FileTypeConverter;
import core.Session;
import core.SessionFactory;
import filters.ComparisonRule;
import filters.CorrectTrialsOnlyFilter;
import filters.ReactionTimeFilter;

public class StroopAnalysis {

public static void main(String[] args) throws Exception
{
	String dir = "C:/information/object_stroop/interference_across/";
	String bird = "subject";
	String workDir = dir + bird + "/";

	FileTypeConverter.CreateZipFileFromDirectory(workDir, bird, new StroopSession());
	File zipFile = new File(workDir + bird + ".dbo");
	Vector<Session> sessions = SessionFactory.BuildSessions(new StroopSession(), zipFile);

	Analysis analysis = new Analysis(sessions);


	analysis.addMap(new SessionInfoMap());
	analysis.addFilter(new ReactionTimeFilter(
			new ComparisonRule(ComparisonRule.LT_OR_EQ, 301)));
	analysis.addFilter(new CorrectTrialsOnlyFilter());
	analysis.addSplitter(new ConfigurationSplitter());
	analysis.addMap(new AverageRTMap());
	analysis.analyze();
}
}

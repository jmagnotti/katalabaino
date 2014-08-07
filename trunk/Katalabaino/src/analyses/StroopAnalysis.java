package analyses;

import java.io.File;
import java.util.Vector;

import mappers.MeanResponseTimeMap;
import mappers.SessionInformationMap;
import sessions.StroopSession;
import splitters.ConfigurationSplitter;
import core.ComparisonRule;
import core.analysis.Analysis;
import core.session.Session;
import core.session.SessionFactory;
import file.FileTypeConverter;
import filters.CorrectTrialsOnlyFilter;
import filters.ResponseTimeFilter;

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


	analysis.addMap(new SessionInformationMap());
	analysis.addFilter(new ResponseTimeFilter(
			new ComparisonRule(ComparisonRule.LT_OR_EQ, 301)));
	analysis.addFilter(new CorrectTrialsOnlyFilter());
	analysis.addSplitter(new ConfigurationSplitter());
	analysis.addMap(new MeanResponseTimeMap());
	analysis.analyze();
}
}

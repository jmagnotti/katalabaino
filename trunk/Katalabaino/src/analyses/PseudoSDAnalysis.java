package analyses;

import java.io.File;
import java.util.Vector;

import mappers.AccuracyMap;
import mappers.SessionInfoMap;
import sessions.PseudoSDSession;
import splitters.PseudoTrueSplitter;
import testing.ItemPairAccuracyMap;
import testing.MultiSessionHDAnalysis;
import core.Analysis;
import core.FileTypeConverter;
import core.Session;
import core.SessionFactory;
import filters.ComparisonRule;
import filters.SessionIDFilter;

public class PseudoSDAnalysis
{
	public static void main(String[] args) throws Exception
	{
		String dir = "W:/warehouse/pseudo/acquisition/";
		// String dir = "/Users/jmagnotti/warehouse/pseudo/8_acquisition/";
		String bird = "shredder";
		String workDir = dir + bird + "/";

		 FileTypeConverter.CreateZipFileFromDirectory(workDir, bird, new PseudoSDSession());
		File zipFile = new File(workDir + bird + ".dbo");

		Vector<Session> sessions = SessionFactory.BuildSessions(new PseudoSDSession(), zipFile);

		Analysis analysis = new Analysis(sessions);
		analysis.addMap(new SessionInfoMap());
		analysis.addMap(new AccuracyMap());
		analysis.addSplitter(new PseudoTrueSplitter());

		// analysis.analyze();

		HDAnalysis(workDir, bird);
	}

	public static void HDAnalysis(String workDir, String bird) throws Exception
	{
		int blockSize = 10;
		int block = 2;
		// for (int block = 0; block < 48 / blockSize; block++) {
		File zipFile = new File(workDir + bird + ".dbo");
		Vector<Session> sessions = SessionFactory.BuildSessions(new PseudoSDSession(), zipFile);

		MultiSessionHDAnalysis analysis = new MultiSessionHDAnalysis(sessions);

		analysis.addFilter(new SessionIDFilter(new ComparisonRule(ComparisonRule.INCLUSIVE, 
//				(block) * blockSize + 1
				1
				, (block + 1) * blockSize))
		);

		// analysis.addFilter(new TrialConfigurationFilter(new ComparisonRule(
		// ComparisonRule.INCLUSIVE, (block * blockSize) + 1, (block + 1) * blockSize)));

		analysis.addMap(new ItemPairAccuracyMap());

		analysis.analyze();
		// }
	}
}

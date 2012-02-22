package analyses;

import java.io.File;
import java.util.Vector;

import mappers.AccuracyMap;
import mappers.SessionInfoMap;
import sessions.FC_CDSession;
import splitters.SampleSetSizeSplitter;
import core.Analysis;
import core.FileTypeConverter;
import core.Session;
import core.SessionFactory;
import filters.ComparisonRule;
import filters.SessionIDFilter;

public class FC_CDAnalysis
{
	public static void main(String[] args) throws Exception
	{

		String dir = "Y:/warehouse/FC_cd/ds7to10/";
		String bird = "deuce";
		String workDir = dir + bird + "/";

		FileTypeConverter.CreateZipFileFromDirectory(workDir, bird, new FC_CDSession());

		File zipFile = new File(workDir + bird + ".dbo");
		Vector<Session> sessions = SessionFactory.BuildSessions(new FC_CDSession(), zipFile);

		Analysis analysis = new Analysis(sessions);

		SessionIDFilter sidf = new SessionIDFilter(new ComparisonRule(ComparisonRule.GREATER_THAN,
				Integer.parseInt(sessions.lastElement().id) - 55));

		analysis.addMap(new SessionInfoMap());
		analysis.addMap(new AccuracyMap());
		// analysis.addMap(new CountMap());
		// analysis.addSplitter(new CD_SamplePeckSplitter());
		// analysis.addSplitter(new PeckNoPeckSplitter());
		// analysis.addFilter(new CPFilter(CPFilter.CP_OFF));
		 analysis.addSplitter(new SampleSetSizeSplitter());

		// analysis.addFilter(new ProbeDelayFilter(0));

		// analysis.addMap(new SamplePecksAverageMap());
//		analysis.addFilter(new SampleSetSizeFilter(new ComparisonRule(ComparisonRule.NOT_EQUAL_TO,
//				1)));
//		analysis.addFilter(sidf);
		analysis.analyze();

		// PositionAnalysis paa = new PositionAnalysis(sessions);
		// paa.addFilter(new SampleSetSizeFilter(new ComparisonRule(ComparisonRule.NOT_EQUAL_TO,
		// 1)));
		// paa.addFilter();
		// paa.analyze();

		// ColorRTAnalysis crta = new ColorRTAnalysis(sessions);
		// crta.addFilter(new ReactionTimeFilter(ReactionTimeFilter.FOUR_SIGMA));
		// crta.addFilter(new SampleSetSizeFilter(1));
		// crta.addFilter(sidf);
		//
		// crta.analyze();
		//
		// MultiSessionHDAnalysis mshda = new MultiSessionHDAnalysis(sessions);
		// sidf = new SessionIDFilter(new ComparisonRule(ComparisonRule.GREATER_THAN,
		// Integer.parseInt(sessions.lastElement().id) - 70));
		//
		// mshda.addFilter(sidf);
		// mshda.addFilter(new SampleSetSizeFilter(2));
		// mshda.addMap(new ItemPairAccuracyMap());
		// mshda.analyze();
	}
}

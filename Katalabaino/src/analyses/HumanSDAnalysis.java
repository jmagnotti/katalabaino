package analyses;

import java.io.File;
import java.util.Vector;

import mappers.MeanResponseTimeMap;
import mappers.SessionInformationMap;
import sessions.HumanSDSession;
import sessions.PseudoSDSession;
import splitters.BlockSplitter;
import splitters.TrialTypeSplitter;
import testing.ItemPairAccuracyMap;
import core.ComparisonRule;
import core.analysis.Analysis;
import core.analysis.UnstructuredAnalysis;
import core.session.Session;
import core.session.SessionFactory;
import file.FileTypeConverter;
import filters.SessionNumberFilter;
import filters.TrialFilter;

public class HumanSDAnalysis
{
	public static void main(String[] args) throws Exception
	{
		String dir = "Y:/warehouse/human_sd/";
		String groups[] = { "fa2010SC_c", "sp2011SC_c" };

		// for (String group : groups)
		// FileTypeConverter.CreateZipFileFromDirectory(dir + group + "/", group,
		// new HumanSDSession());

		// for (String group : groups) {
		// File zipFile = new File(dir + group + "/" + group + ".dbo");
		//
		// Vector<Session> sessions = SessionFactory.BuildSessions(new HumanSDSession(), zipFile);
		//
		// Analysis analysis = new Analysis(sessions);
		// analysis.addMap(new SessionInfoMap());
		// analysis.addMap(new AverageRTMap());
		//
		// analysis.addSplitter(new TrialTypeSplitter());
		//
		// // analysis.addSplitter(new BaselineTransferSplitter());
		// analysis.addSplitter(new BlockSplitter(4, 116));
		// analysis.analyze();
		// }

		for (String group : groups) {
			int blockSize = 16;
			for (int block = 0; block < 16 / blockSize; block++) {
				File zipFile = new File(dir + group + ".dbo");
				Vector<Session> sessions = SessionFactory.BuildSessions(new PseudoSDSession(),
						zipFile);

				UnstructuredAnalysis analysis = new UnstructuredAnalysis(sessions);

				analysis.addFilter(new TrialFilter(new ComparisonRule(ComparisonRule.INCLUSIVE,
						(blockSize * block) + 1, blockSize * block)));

				analysis.addMap(new ItemPairAccuracyMap());

				analysis.analyze();
			}
		}
	}
}

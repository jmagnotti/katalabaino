package analyses;

import java.io.File;
import java.util.Vector;

import mappers.PercentCorrectMap;
import mappers.CountMap;
import sessions.HumanSDSession;
import sessions.PseudoSDSession;
import splitters.BaselineTransferSplitter;
import splitters.BlockSplitter;
import splitters.PseudoTrueSplitter;
import splitters.TrialTypeSplitter;
import testing.ItemPairAccuracyMap;
import core.ComparisonRule;
import core.MultiClassRule;
import core.Splitter;
import core.analysis.Analysis;
import core.analysis.UnstructuredAnalysis;
import core.session.Session;
import core.session.SessionFactory;
import core.trial.Trial;
import file.FileTypeConverter;
import filters.TrialConfigurationCountFilter;

public class HumanPseudoSDAnalysis
{
	public static void main(String[] args) throws Exception
	{
		String dir = "Y:/warehouse/human_pseudo/";
		// String dir = "/Users/jmagnotti/warehouse/human_pseudo/";
		String bird = "pilot";
		String workDir = dir + bird + "/";

		File zipFile = new File(workDir + bird + ".dbo");
		// FileTypeConverter.CreateZipFileFromDirectory(workDir, bird, new HumanSDSession());

		Vector<Session> sessions = SessionFactory.BuildSessions(new HumanSDSession(), zipFile);

		Analysis analysis = new Analysis(sessions);
		analysis.addMap(new PercentCorrectMap());
		// analysis.addSplitter(new TrialTypeSplitter());
		analysis.addSplitter(new BaselineTransferSplitter());
		// analysis.addSplitter(new BlockSplitter(20, 100));

		analysis.addSplitter(new Splitter(new MultiClassRule() {

			@Override
			public String getClassMembership(Trial trial)
			{
				String cls = "bk";
				int block = 0;

				if (trial.trialNumber > 112) {
					block = (trial.trialNumber - 113) / 6 + 28;
				}
				else {
					block = (trial.trialNumber-1) / 4;
				}
				
				if (block < 9)
					cls = cls + "0";

				return cls + (block + 1) + ".";
			}
		}));

		analysis.analyze();

		UnstructuredAnalysis mshdAnalysis = new UnstructuredAnalysis(sessions);

		// analysis.addFilter(new SessionIDFilter(new ComparisonRule(ComparisonRule.INCLUSIVE,
		// (block) * blockSize + 1, (block + 1) * blockSize)));

		mshdAnalysis.addMap(new ItemPairAccuracyMap());

		// mshdAnalysis.analyze();

	}
}

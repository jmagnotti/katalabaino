package analyses;

import core.ComparisonRule;
import core.analysis.TypicalAnalysis;
import mappers.PercentCorrectMap;
import mappers.SessionInformationMap;
import sessions.YN_CDSession;
import splitters.ConfigurationSplitter;
import splitters.ProbeDelaySplitter;
import splitters.SampleSetSizeSplitter;
import splitters.YNPeckSplitter;
import splitters.YN_PeckDistanceSplitter;
import filters.ProbeDelayFilter;
import filters.SampleSetSizeFilter;
import filters.SessionNameFilter;

public class YN_CDAnalysis extends TypicalAnalysis {

	public YN_CDAnalysis(String directory, String bird) {
		super(directory, bird, new YN_CDSession());
	}

	@Override
	public void do_analyze() {

		analysis.addMap(new SessionInformationMap());
		analysis.addMap(new PercentCorrectMap());
		analysis.addFilter(new SessionNameFilter("fr_3"));
		analysis.addSplitter(new ConfigurationSplitter("icm"));
		analysis.addSplitter(new ProbeDelaySplitter());
		analysis.addSplitter(new SampleSetSizeSplitter());
		analysis.analyze(true);

		analysis.addMap(new PercentCorrectMap());
		analysis.addFilter(new SessionNameFilter("fr_3"));
		analysis.addFilter(new SampleSetSizeFilter(new ComparisonRule(ComparisonRule.GREATER_THAN, 1)));
		analysis.addSplitter(new YN_PeckDistanceSplitter());
		analysis.analyze(true);

		analysis.addMap(new PercentCorrectMap());
		analysis.addFilter(new SessionNameFilter("fr_3"));
		analysis.addFilter(new SampleSetSizeFilter(3));
		analysis.addSplitter(new YNPeckSplitter());
		analysis.addSplitter(new ProbeDelaySplitter());
		analysis.analyze();
	}

	public static void main(String[] args) throws Exception {
		// String dir = "Z:/warehouse/YN_CD/acquisition/";
		String dir = "/Users/jmagnotti/warehouse/YN_CD/acquisition/";
		String[] birds = { "ted", "mark", "curly" };

		for (String bird : birds) {
			Thread t = new Thread(new YN_CDAnalysis(dir, bird));
			t.start();
		}
	}

}

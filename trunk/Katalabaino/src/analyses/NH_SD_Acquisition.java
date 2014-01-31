package analyses;

import mappers.IncorrectCorrectionsMap;
import mappers.MeanResponseTimeMap;
import mappers.MeanSampleCompletionResponseTimeMap;
import mappers.MeanSampleResponseMap;
import mappers.PercentCorrectMap;
import mappers.SessionInformationMap;
import sessions.SDSession;
import splitters.TrialTypeSplitter;
import core.ComparisonRule;
import filters.CorrectTrialsOnlyFilter;
import filters.ResponseTimeFilter;
import filters.SampleResponseResponseTimeFilter;

public class NH_SD_Acquisition extends TypicalAnalysis {

	public NH_SD_Acquisition(String dir, String bird) {
		super(dir, bird, new SDSession());
	}

	@Override
	public void do_analyze() {
		analysis.addMap(new SessionInformationMap());
		analysis.addMap(new PercentCorrectMap());
		analysis.analyze();

		analysis.addSplitter(new TrialTypeSplitter(true));
		analysis.analyze();

		// only clearing maps, so the splitter is still in
		// effect
		analysis.clearMaps();
		analysis.addMap(new SessionInformationMap());
		analysis.addMap(new MeanResponseTimeMap());
		analysis.addFilter(new ResponseTimeFilter(new ComparisonRule(ComparisonRule.LT_OR_EQ, 10 * 1000)));
		analysis.addFilter(new CorrectTrialsOnlyFilter());
		analysis.analyze(true);

		analysis.addMap(new SessionInformationMap());
		analysis.addMap(new MeanSampleCompletionResponseTimeMap());
		analysis.addFilter(new SampleResponseResponseTimeFilter(1));
		analysis.analyze(true);

		analysis.addMap(new SessionInformationMap());
		analysis.addMap(new IncorrectCorrectionsMap());
		analysis.analyze(true);

		analysis.addMap(new SessionInformationMap());
		analysis.addMap(new MeanSampleResponseMap());
		analysis.addFilter(new SampleResponseResponseTimeFilter(1));
		analysis.analyze();
	}

	public static void main(String[] args) throws Exception {
		String dir = "Z:/warehouse/magpie/";
		// String[] birds = { "blue", "blueA0", "blueA3", "red", "red004",
		// "redx01", "yellow" };
		String[] birds = { "red004" };
		for (String bird : birds) {
			Thread t = new Thread(new NH_SD_Acquisition(dir, bird));
			t.start();
		}
	}
}

package analyses;

import mappers.CorrectionProcedureStatusMap;
import mappers.MedianResponseTimeMap;
import mappers.PercentCorrectMap;
import mappers.SampleResponseInformationMap;
import mappers.SessionInformationMap;
import sessions.N_ItemMTSSession;
import splitters.ChoiceSetSizeSplitter;
import core.ComparisonRule;
import filters.CorrectTrialsOnlyFilter;
import filters.ResponseTimeFilter;

public class NItemMTSAnalysis extends TypicalAnalysis {
	public NItemMTSAnalysis(String dir, String bird) {
		super(dir, bird, new N_ItemMTSSession());
	}

	@Override
	public void do_analyze() {
		analysis.addMap(new SessionInformationMap());
		analysis.addSplitter(new ChoiceSetSizeSplitter());
		analysis.addFilter(new ResponseTimeFilter(new ComparisonRule(ComparisonRule.LT_OR_EQ, 10000)));
		analysis.addFilter(new CorrectTrialsOnlyFilter());
		analysis.addMap(new CorrectionProcedureStatusMap());
		analysis.addMap(new MedianResponseTimeMap());
		analysis.analyze(true);

		analysis.addMap(new PercentCorrectMap());
		analysis.addSplitter(new ChoiceSetSizeSplitter());
		analysis.analyze(true);

		analysis.addMap(new SampleResponseInformationMap());
		analysis.analyze();
	}

	public static void main(String[] args) throws Exception {
		String loc = "Z:/Dropbox/AU_Projects/katz/target_search/For John/n-item_mts/";
		String[] phases = {"shape_train/", "8_poly/",  "4_poly", "Line_Segments/"};
		String[] birds = { "oleg", "jupiter", "emil", "Hesse", "mars", "sinclair"};

		for(String dir : phases){
		for (String bird : birds) {
			Thread t = new Thread(new NItemMTSAnalysis(loc + dir, bird));
			t.start();
		}}
	}

}

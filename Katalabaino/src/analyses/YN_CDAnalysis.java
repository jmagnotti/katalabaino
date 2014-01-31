package analyses;

import mappers.PercentCorrectMap;
import mappers.SessionInformationMap;
import sessions.YN_CDSession;
import splitters.ConfigurationSplitter;
import splitters.ProbeDelaySplitter;
import splitters.SampleSetSizeSplitter;
import filters.SessionNameFilter;

public class YN_CDAnalysis extends TypicalAnalysis {

	public YN_CDAnalysis(String directory, String bird) {
		super(directory, bird, new YN_CDSession());
	}

	@Override
	public void do_analyze() {
		analysis.addFilter(new SessionNameFilter("fr_3"));

		analysis.addMap(new SessionInformationMap());
		analysis.addMap(new PercentCorrectMap());

		analysis.addSplitter(new SampleSetSizeSplitter());
		analysis.analyze();

		analysis.addSplitter(new ProbeDelaySplitter());
		analysis.analyze();

		analysis.addSplitter(new ConfigurationSplitter("icm"));
		analysis.analyze();
	}

	public static void main(String[] args) throws Exception {
		String dir = "Z:/warehouse/YN_CD/acquisition/";
		String[] birds = { "ted", "mark", "curly" };

		for (String bird : birds) {
			Thread t = new Thread(new YN_CDAnalysis(dir, bird));
			t.start();
		}
	}

}

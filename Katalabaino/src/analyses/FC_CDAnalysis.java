package analyses;

import mappers.CountMap;
import mappers.SessionInformationMap;
import sessions.FC_CDSession;
import splitters.SampleProbeFilenameSplitter;

public class FC_CDAnalysis extends TypicalAnalysis {
	protected FC_CDAnalysis(String dir, String subj) {
		super(dir, subj, new FC_CDSession());
	}

	@Override
	protected void do_analyze() {
		analysis.addMap(new SessionInformationMap());
		analysis.addSplitter(new SampleProbeFilenameSplitter());
		analysis.addMap(new CountMap());

		analysis.analyze();
	}

	public static void main(String[] args) throws Exception {
		String dir = "Z:/Downloads/cdt/";
		String bird = "cisco";

		FC_CDAnalysis fccda = new FC_CDAnalysis(dir, bird);
		fccda.run();
	}
}

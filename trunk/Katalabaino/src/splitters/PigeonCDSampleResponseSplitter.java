package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.trial.Trial;

class PeckLocationRule extends MultiClassRule {

	@Override
	public String getClassMembership(Trial trial) {
		if (trial.sampleResponses.size() == 0)
			return "nop.";

		if (trial.sampleResponses.lastElement().position == trial.correctLocation)
			return "pcor.";

		if (trial.sampleResponses.lastElement().position == 0)
			return "pScn.";

		return "pinc.";
	}

}

public class PigeonCDSampleResponseSplitter extends Splitter {
	public PigeonCDSampleResponseSplitter() {
		super(new PeckLocationRule());
	}
}

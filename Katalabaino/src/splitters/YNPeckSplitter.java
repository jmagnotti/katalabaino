package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.Trial;

class YNPeckLocationRule extends MultiClassRule {

	@Override
	public String getClassMembership(Trial trial) {

		if (trial.sampleSetSize == -99)
			return "wu.";

		if (trial.sampleResponses.size() < 1)
			return "nop.";

		if (trial.sampleResponses.lastElement().position == trial.choiceStimuli
				.get(0).position)
			return "pcor.";

		return "pinc.";
	}

}

public class YNPeckSplitter extends Splitter {
	public YNPeckSplitter() {
		super(new YNPeckLocationRule());
	}
}

package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.trial.Trial;

class ProbeLabelRule extends MultiClassRule {
	@Override
	public String getClassMembership(Trial trial) {
		return trial.choiceStimuli.firstElement().label + ".";
	}
}

public class ProbeLabelSplitter extends Splitter {
	public ProbeLabelSplitter() {
		super(new ProbeLabelRule());
	}
}

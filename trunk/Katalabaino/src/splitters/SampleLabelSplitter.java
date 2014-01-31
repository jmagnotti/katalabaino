package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.Trial;

class SampleLabelRule extends MultiClassRule {
	@Override
	public String getClassMembership(Trial trial) {
		return trial.sampleStimuli.firstElement().label + ".";
	}
}

public class SampleLabelSplitter extends Splitter {
	public SampleLabelSplitter() {
		super(new SampleLabelRule());
	}
}

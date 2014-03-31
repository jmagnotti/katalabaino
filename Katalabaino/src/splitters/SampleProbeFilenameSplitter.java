package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.Trial;

class SampleProbeFileNameRule extends MultiClassRule {

	@Override
	public String getClassMembership(Trial trial) {
		return trial.sampleStimuli.firstElement().file + ":" + trial.choiceStimuli.lastElement().file;
	}

}

public class SampleProbeFilenameSplitter extends Splitter {
	public SampleProbeFilenameSplitter() {
		super(new SampleProbeFileNameRule());
	}
}

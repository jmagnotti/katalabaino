package splitters;

import core.Colors;
import core.MultiClassRule;
import core.Splitter;
import core.Trial;

class SampleClassRule extends MultiClassRule {
	@Override
	public String getClassMembership(Trial trial) {
		String cls = "";

		int cid = trial.sampleStimuli.firstElement().colorID;

		if (Colors.GetInstance().isBasicColor(cid))
			cls = "Color";
		else
			cls = Colors.GetInstance().colorIDToLabel.get(cid);

		return cls + ".";
	}
}

public class SampleClassSplitter extends Splitter {
	public SampleClassSplitter() {
		super(new SampleClassRule());
	}
}

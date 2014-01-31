package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.Trial;

class PositionSplitRule extends MultiClassRule {
	@Override
	public String getClassMembership(Trial trial) {
		int p = trial.correctLocation;
		if (p < 10)
			return "p0" + p;

		return "p" + p;
	}

}

public class CorrectPositionSplitter extends Splitter {
	public CorrectPositionSplitter() {
		super(new PositionSplitRule());
	}
}

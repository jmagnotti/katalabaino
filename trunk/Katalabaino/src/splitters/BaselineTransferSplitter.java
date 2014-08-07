package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.trial.Trial;

class BaselineTransferRule extends MultiClassRule {
	@Override
	public String getClassMembership(Trial trial) {
		if (trial.isTransfer)
			return "t.";

		return "b.";
	}

}

public class BaselineTransferSplitter extends Splitter {
	public BaselineTransferSplitter() {
		super(new BaselineTransferRule());
	}
}

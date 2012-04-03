package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.Trial;

class PseudoRule extends MultiClassRule
{
	@Override
	public String getClassMembership(Trial trial)
	{
		String sample, probe;

		sample = trial.sampleStimuli.get(0).file;
		probe = trial.choiceStimuli.get(0).file;

		if (sample.equals(probe)) {
			if (trial.trialType.equals("Same")) return "TrueSame";

			return "PseudoDiff";
		}

		if (trial.trialType.equals("Same")) return "PseudoSame";

		return "TrueDiff";
	}
}

public class PseudoTrueSplitter extends Splitter
{
	public PseudoTrueSplitter()
	{
		super(new PseudoRule());
	}
}

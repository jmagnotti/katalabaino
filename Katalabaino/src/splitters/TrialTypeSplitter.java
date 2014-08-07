package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.trial.Trial;

class TrialTypeRule extends MultiClassRule
{
	private boolean	sameFirst;

	public TrialTypeRule()
	{
		this(false);
	}

	public TrialTypeRule(boolean forceSameFirst)
	{
		sameFirst = forceSameFirst;
	}

	@Override
	public String getClassMembership(Trial trial)
	{

		if (sameFirst) {
			if (trial.trialType.equalsIgnoreCase("Same")) return "_same.";
		}
		
		return trial.trialType.toLowerCase() + ".";
	}

}

public class TrialTypeSplitter extends Splitter
{
	public TrialTypeSplitter()
	{
		super(new TrialTypeRule());
	}

	public TrialTypeSplitter(boolean forceSameFirst)
	{
		super(new TrialTypeRule(forceSameFirst));
	}
}

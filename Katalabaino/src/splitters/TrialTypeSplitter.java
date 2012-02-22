package splitters;

import core.Trial;

class TrialTypeRule extends MultiClassRule
{

	@Override
	public String getClassMembership(Trial trial)
	{
		
		if(trial.trialType.equalsIgnoreCase("Same"))
			return "_same.";
	
		return trial.trialType.toLowerCase() + ".";	
	}

}

public class TrialTypeSplitter extends Splitter
{
	public TrialTypeSplitter()
	{
		super(new TrialTypeRule());
	}
}

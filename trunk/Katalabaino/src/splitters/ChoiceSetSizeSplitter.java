package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.Trial;

class ChoiceSetSizeRule extends MultiClassRule
{
	@Override
	public String getClassMembership(Trial trial)
	{
		return "css" + trial.choiceSetSize;
	}
}

public class ChoiceSetSizeSplitter extends Splitter
{
	public ChoiceSetSizeSplitter()
	{
		super(new ChoiceSetSizeRule());
	}
}

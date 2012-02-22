package splitters;

import core.Trial;

class PeckRule extends MultiClassRule
{
	public PeckRule()
	{}

	@Override
	public String getClassMembership(Trial trial)
	{
		if (trial.sampleResponses.size() == 0)
			return "p0";
		else
			return "p1";
	}
}

public class PeckNoPeckSplitter extends Splitter
{
	public PeckNoPeckSplitter()
	{
		super(new PeckRule());
	}
}

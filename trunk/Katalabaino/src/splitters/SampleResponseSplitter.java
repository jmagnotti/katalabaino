package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.trial.Trial;

class SampleResponseRule extends MultiClassRule
{
	public SampleResponseRule()
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

public class SampleResponseSplitter extends Splitter
{
	public SampleResponseSplitter()
	{
		super(new SampleResponseRule());
	}
}

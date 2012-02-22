package splitters;

import core.Trial;

class SampleSetSizeRule extends MultiClassRule
{
	@Override
	public String getClassMembership(Trial trial)
	{
		return "s" + trial.sampleSetSize + ".";
	}
}

public class SampleSetSizeSplitter extends Splitter
{
	public SampleSetSizeSplitter()
	{
		super(new SampleSetSizeRule());
	}

}

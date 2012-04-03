package splitters;

import core.MultiClassRule;
import core.Splitter;
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

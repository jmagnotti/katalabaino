package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.trial.Colors;
import core.trial.Trial;


class SampleColorRule extends MultiClassRule
{

	@Override
	public String getClassMembership(Trial trial)
	{
		return Colors.GetInstance().colorIDToLabel.get(trial.sampleStimuli.firstElement().colorID) + ".";
	}
	
}

public class SampleColorSplitter extends Splitter
{
	public SampleColorSplitter()
	{
		super(new SampleColorRule());
	}
}

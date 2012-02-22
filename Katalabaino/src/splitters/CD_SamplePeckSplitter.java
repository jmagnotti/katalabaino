package splitters;

import core.Trial;

class PeckLocationRule extends MultiClassRule
{

	@Override
	public String getClassMembership(Trial trial)
	{
		if (trial.sampleResponses.size() == 0) return "nop.";

		if (trial.sampleResponses.lastElement().position == trial.correctLocation) return "pcor.";

		if (trial.sampleResponses.lastElement().position == 0) return "pScn.";

		return "pinc.";
	}

}

public class CD_SamplePeckSplitter extends Splitter
{
	public CD_SamplePeckSplitter()
	{
		super(new PeckLocationRule());
	}
}

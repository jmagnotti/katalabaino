package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.trial.SampleResponse;
import core.trial.Trial;

class ViewTimeAbortRule extends MultiClassRule
{

	@Override
	public String getClassMembership(Trial trial)
	{
		int vta = 0;
		for (SampleResponse sr : trial.sampleResponses)
			vta += sr.viewTimeAbort;

		if (vta > 0) return "vta";

		return "no_vta";
	}

}

public class ViewTimeAbortSplitter extends Splitter
{
	public ViewTimeAbortSplitter()
	{
		super(new ViewTimeAbortRule());
	}
}

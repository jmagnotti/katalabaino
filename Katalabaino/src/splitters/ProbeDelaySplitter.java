package splitters;

import java.text.DecimalFormat;

import core.Trial;

class ProbeDelayRule extends MultiClassRule
{
	private DecimalFormat	df;

	public ProbeDelayRule()
	{
		df = new DecimalFormat("#");
	}

	@Override
	public String getClassMembership(Trial trial)
	{
		return "pd" + df.format(trial.probeDelay);
	}

}

public class ProbeDelaySplitter extends Splitter
{
	public ProbeDelaySplitter()
	{
		super(new ProbeDelayRule());
	}
}

package splitters;

import java.text.DecimalFormat;

import core.MultiClassRule;
import core.Splitter;
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
		String pd = df.format(trial.probeDelay);
		while (pd.length() < 4)
			pd = "0" + pd;

		return "pd" + pd;
	}

}

public class ProbeDelaySplitter extends Splitter
{
	public ProbeDelaySplitter()
	{
		super(new ProbeDelayRule());
	}
}

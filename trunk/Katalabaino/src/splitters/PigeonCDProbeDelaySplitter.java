package splitters;

import java.util.HashMap;

import core.MultiClassRule;
import core.Splitter;
import core.trial.Trial;

class CDProbeDelayRule extends MultiClassRule
{
	private HashMap<Double, String>	mapValues;

	public CDProbeDelayRule()
	{
		mapValues = new HashMap<Double, String>();

		mapValues.put(0.0, "0");
		mapValues.put(12.0, "1");
		mapValues.put(24.0, "2");
		mapValues.put(36.0, "3");
		mapValues.put(48.0, "4");
		mapValues.put(60.0, "5");
		mapValues.put(72.0, "6");
		mapValues.put(100.0, "7");
	}

	@Override
	public String getClassMembership(Trial trial)
	{
		return "pd" + mapValues.get(trial.probeDelay);
	}

}

public class PigeonCDProbeDelaySplitter extends Splitter
{
	public PigeonCDProbeDelaySplitter()
	{
		super(new CDProbeDelayRule());
	}
}

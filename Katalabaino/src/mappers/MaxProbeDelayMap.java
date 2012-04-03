package mappers;

import java.util.Vector;

import core.Mapper;
import core.Session;
import core.Trial;

public class MaxProbeDelayMap extends Mapper
{
	private int maxPD;

	public MaxProbeDelayMap()
	{
		super("maxPD");
	}
	
	
	@Override
	public void nextSession(Session session)
	{
		maxPD = -1;
	}

	@Override
	public void nextTrial(Trial trial)
	{
		if (trial.probeDelay > maxPD)
			maxPD = (int) trial.probeDelay;
	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString = new Vector<String>();
		resultString.add(""+maxPD);
		return resultString;
	}

}

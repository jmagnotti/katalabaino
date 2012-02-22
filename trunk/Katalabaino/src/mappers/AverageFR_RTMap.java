package mappers;

import java.util.Vector;

import core.Session;
import core.Trial;

public class AverageFR_RTMap extends Mapper
{

	private double	rt, count;

	public AverageFR_RTMap()
	{
		super("avgFR_RT");
	}

	@Override
	public void nextSession(Session session)
	{}

	@Override
	public void nextTrial(Trial trial)
	{
		count = count + 1;
		rt += trial.sampleResponses.lastElement().responseTime;
	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString = new Vector<String>();
		resultString.add("" + (rt / count));

		rt = count = 0.0;

		return resultString;
	}

}

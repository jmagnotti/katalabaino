package mappers;

import java.util.Vector;

import core.Session;
import core.Trial;

public class AverageRTMap extends Mapper
{

	private double	rt, count;

	public AverageRTMap()
	{
		super("avgRT");
	}

	@Override
	public void nextSession(Session session)
	{}

	@Override
	public void nextTrial(Trial trial)
	{
		count = count + 1;
		rt += trial.responseTime;
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

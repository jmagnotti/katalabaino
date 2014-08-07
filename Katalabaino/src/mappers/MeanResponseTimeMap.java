package mappers;

import java.util.Vector;

import core.Mapper;
import core.session.Session;
import core.trial.Trial;

public class MeanResponseTimeMap extends Mapper
{

	private double	rt, count;

	public MeanResponseTimeMap()
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

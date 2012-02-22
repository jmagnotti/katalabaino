package mappers;

import java.util.Vector;

import core.Session;
import core.Trial;

public class ViewTimeMap extends Mapper
{

	double	vt, count;

	protected ViewTimeMap()
	{
		super("vt");
	}

	@Override
	public void nextSession(Session session)
	{
		resultString = new Vector<String>();
		count = vt = 0.0;
	}

	@Override
	public void nextTrial(Trial trial)
	{
		vt += trial.actualViewTime;
		count++;
	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString.add("" + (.001*vt/count));
		return resultString;
	}

}

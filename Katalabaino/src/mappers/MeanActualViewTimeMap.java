package mappers;

import java.util.Vector;

import core.Mapper;
import core.Session;
import core.Trial;

/**
 * Make sure you know what unit your VT is stored in--likely ms.
 */
public class MeanActualViewTimeMap extends Mapper
{
	double	vt, count;

	public MeanActualViewTimeMap()
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
		resultString.add("" + (vt / count));
		return resultString;
	}

}

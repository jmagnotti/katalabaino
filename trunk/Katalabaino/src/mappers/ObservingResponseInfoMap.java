package mappers;

import java.util.Vector;

import core.Session;
import core.Trial;

public class ObservingResponseInfoMap extends Mapper
{
	private int	fr;

	public ObservingResponseInfoMap()
	{
		super("FR");
	}

	@Override
	public void nextSession(Session session)
	{
		resultString = new Vector<String>();
		fr = session.trials.lastElement().observingResponse;
		resultString.add("" + fr);
	}

	@Override
	public void nextTrial(Trial trial)
	{}

	@Override
	public Vector<String> cleanUp()
	{
		return resultString;
	}

}

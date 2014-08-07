package mappers;

import java.util.Vector;

import core.Mapper;
import core.session.Session;
import core.trial.Trial;

public class CountMap extends Mapper
{
	private int	counter;

	public CountMap()
	{
		super("num");
	}

	@Override
	public void nextSession(Session session)
	{
		resultString = new Vector<String>();
		counter = 0;
	}

	@Override
	public void nextTrial(Trial trial)
	{
		counter++;
	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString.clear();
		resultString.add("" + counter);
		return resultString;
	}

}

package mappers;

import java.util.Vector;

import core.Mapper;
import core.session.Session;
import core.trial.Trial;

public class SampleResponseInformationMap extends Mapper
{
	private int	fr;

	public SampleResponseInformationMap()
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

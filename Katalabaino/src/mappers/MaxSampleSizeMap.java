package mappers;

import java.util.Vector;

import core.Mapper;
import core.session.Session;
import core.trial.Trial;

public class MaxSampleSizeMap extends Mapper
{

	int	sampleSize;

	public MaxSampleSizeMap()
	{
		super("maxSS");
	}

	@Override
	public void nextSession(Session session)
	{
		sampleSize = -1;
	}

	@Override
	public void nextTrial(Trial trial)
	{
		if (trial.sampleSetSize > sampleSize) sampleSize = trial.sampleSetSize;

	}
	
	@Override
	public boolean allowSplits()
	{
		return false;
	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString = new Vector<String>();
		resultString.add("" + sampleSize);

		return resultString;
	}

}

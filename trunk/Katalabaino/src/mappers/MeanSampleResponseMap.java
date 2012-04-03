package mappers;

import java.util.Vector;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import core.Mapper;
import core.Session;
import core.Trial;

public class MeanSampleResponseMap extends Mapper
{

	private DescriptiveStatistics	ds;

	public MeanSampleResponseMap()
	{
		super("fr");
		ds = new DescriptiveStatistics();
	}

	@Override
	public void nextSession(Session session)
	{
		ds = new DescriptiveStatistics();
	}

	@Override
	public void nextTrial(Trial trial)
	{
		ds.addValue(trial.sampleResponses.size());
	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString = new Vector<String>();

		resultString.add("" + ds.getMean());

		return resultString;
	}

}

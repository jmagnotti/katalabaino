package mappers;

import java.util.Vector;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import core.Session;
import core.Trial;

public class AverageFRMap extends Mapper
{

	private DescriptiveStatistics	ds;

	public AverageFRMap()
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

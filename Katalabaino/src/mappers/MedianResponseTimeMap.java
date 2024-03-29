package mappers;

import java.util.Vector;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import core.Mapper;
import core.session.Session;
import core.trial.Trial;

public class MedianResponseTimeMap extends Mapper
{
	private DescriptiveStatistics	stats;

	public MedianResponseTimeMap()
	{
		super("medRT");
	}

	@Override
	public void nextSession(Session session)
	{
		stats = new DescriptiveStatistics();
	}

	@Override
	public void nextTrial(Trial trial)
	{
		stats.addValue(trial.responseTime);
	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString = new Vector<String>();

		resultString.add("" + stats.getPercentile(50));

		return resultString;
	}

}

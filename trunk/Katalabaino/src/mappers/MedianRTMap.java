package mappers;

import java.util.Vector;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import core.Session;
import core.Trial;

public class MedianRTMap extends Mapper
{
	private DescriptiveStatistics	stats;

	public MedianRTMap()
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

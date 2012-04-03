package mappers;

import java.util.Vector;

import core.Mapper;
import core.Session;
import core.Trial;

public class MeanSampleCompletionResponseTimeMap extends Mapper
{

	private double	rt, count, currMax;

	public MeanSampleCompletionResponseTimeMap()
	{
		super("avgFR_RT");
	}

	@Override
	public void nextSession(Session session)
	{}

	@Override
	public void nextTrial(Trial trial)
	{
		count = count + 1;
		currMax = Double.NEGATIVE_INFINITY;
		// because some sample responses are emitted after the FR is completed (i.e., the RT timer
		// is reset) the last Peck may not be the peck that completed the FR. More importantly, the
		// last peck may be recorded as ``faster'' than the ones prior to it

		// to complicate matters further, we can't be sure of any ordering, so we have to do an
		// exhaustive search :(
		// ##FIXME can this be fixed at the session level?

		for (int i = 0; i < trial.sampleResponses.size(); i++) {
			if (currMax < trial.sampleResponses.get(i).responseTime)
				currMax = trial.sampleResponses.get(i).responseTime;
		}

		rt += currMax;
	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString = new Vector<String>();
		resultString.add("" + (rt / count));

		rt = count = 0.0;

		return resultString;
	}

}

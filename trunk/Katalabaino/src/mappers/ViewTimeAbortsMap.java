package mappers;

import java.util.Vector;

import core.DescriptiveStatistics;
import core.Mapper;
import core.session.Session;
import core.trial.SampleResponse;
import core.trial.Trial;

public class ViewTimeAbortsMap extends Mapper
{
	private double		vtaCount, trialCount, vtaCount2;

	private double[]	vtas;

	public ViewTimeAbortsMap()
	{
		super("vta\tp(vta)\tmed(vta)\tmax(vta)\tp(abort)");
//		super("p(vtA)");
	}

	@Override
	public void nextSession(Session session)
	{
		resultString = new Vector<String>();
		vtaCount = 0;
		trialCount = 0;
		vtaCount2 = 0.0;
		vtas = new double[session.trials.size()];
	}

	@Override
	public void nextTrial(Trial trial)
	{
		trialCount++;

		double maxVTA = 0;

		for (SampleResponse sr : trial.sampleResponses) {
			maxVTA = Math.max(sr.viewTimeAbort, maxVTA);
		}

		vtas[trial.trialNumber - 1] = maxVTA;

		vtaCount2 += maxVTA > 0 ? 1 : 0;

		vtaCount += maxVTA;
	}

	@Override
	public Vector<String> cleanUp()
	{
		double median = DescriptiveStatistics.GetMedian(true, vtas);

		resultString.add("" + vtaCount);

		if (vtaCount > 0)
			resultString.add("" + (vtaCount / trialCount));
		else
			resultString.add("" + Double.NaN);

		resultString.add("" + median);

		resultString.add("" + vtas[vtas.length - 1]);

		resultString.add("" + (vtaCount2 / trialCount));

		return resultString;
	}

}

package calc;

import org.apache.commons.math.DimensionMismatchException;
import org.apache.commons.math.linear.InvalidMatrixException;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import core.PositionHelper;
import core.trial.Trial;

public class MahalanobisHelper
{
	public static double[][] GetSamplePositionData(Trial trial)
	{
		double[][] data = new double[trial.sampleSetSize][2];

		for (int i = 0; i < trial.sampleSetSize; i++) {
			data[i][0] = PositionHelper.PigeonFC_CDLookUp(trial.sampleStimuli.get(i).position,
					PositionHelper.XPOS);
			data[i][1] = PositionHelper.PigeonFC_CDLookUp(trial.sampleStimuli.get(i).position,
					PositionHelper.YPOS);
		}
		return data;
	}

	public static double GetSinglePositionDistance(double[][] samplePoints, double[] observation)
	{
		double distance = 0.0;

		try {
			MahalanobisDistance md = new MahalanobisDistance(samplePoints);
			distance = md.getSingleDistance(observation);
		}
		catch (DimensionMismatchException e) {
			e.printStackTrace();
		}
		catch (InvalidMatrixException ime) {
			// so we can't do the fancy stuff because the matrix is singular. This most likely cause
			// is the stimuli are in a line (i.e., zero variance along a given dimension).
			SummaryStatistics ssX = new SummaryStatistics();
			SummaryStatistics ssY = new SummaryStatistics();

			for (int i = 0; i < samplePoints.length; i++)
				ssX.addValue(samplePoints[i][0]);

			for (int i = 0; i < samplePoints.length; i++)
				ssY.addValue(samplePoints[i][1]);

			// these next two blocks typically won't BOTH be true, but maybe they could? this
			// would occur if the var-cov matrix were singular but BOTH X and Y have nonzero
			// variance. I guess this could occur?

			// if Var(X) > 0, then calc distance on X
			if (ssX.getVariance() > 0) {
				distance = Math.pow((observation[0] - ssX.getMean()) / ssX.getStandardDeviation(),
						2);
			}
			// if Var(Y) > 0, then calc distance on Y
			if (ssY.getVariance() > 0) {
				distance += Math.pow((observation[1] - ssY.getMean()) / ssY.getStandardDeviation(),
						2);
			}
		}
		// using distances rather than d^2, will it matter?
		distance = Math.sqrt(distance);

		return distance;
	}

	/**
	 * hmm... where should this actually go? Maybe each experiment should have specific
	 * "trial-helpers?" or just put methods in Trial?
	 * 
	 * @return
	 */
	public static int GetChangedStimulusIndex(Trial t)
	{
		boolean found = false;
		int index = -1;
		while (!found && index < t.sampleStimuli.size()) {
			index++;
			found = (t.sampleStimuli.get(index).position == t.correctLocation);
		}

		return index;
	}

}

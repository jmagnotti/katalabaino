package mappers;

import java.util.Vector;

import calc.MahalanobisHelper;
import core.Mapper;
import core.analysis.Analysis;
import core.session.Session;
import core.trial.Trial;

/**
 * Finds the Mahalanobis Distance of the changed item. Sigma is based on the X,Y locations of the
 * sample items.
 * 
 * @author jmagnotti
 * 
 */
public class ChangeItemMahalanobisMap extends Mapper
{
	private double	distance;

	public ChangeItemMahalanobisMap()
	{
		super("mahal");
	}

	@Override
	public void nextSession(Session session)
	{}

	@Override
	public void nextTrial(Trial trial)
	{
		if (trial.sampleSetSize > 1) {

			double[][] samplePoints = MahalanobisHelper.GetSamplePositionData(trial);
			distance = MahalanobisHelper.GetSinglePositionDistance(samplePoints,
					samplePoints[MahalanobisHelper.GetChangedStimulusIndex(trial)]);

			resultString.add(distance + Analysis.field_delimiter);
		}
	}

	@Override
	public Vector<String> cleanUp()
	{
		Vector<String> res = new Vector<String>();
		res.addAll(resultString);

		resultString.clear();

		return res;
	}

}

package testing;

import java.util.Vector;

import core.analysis.UnstructuredAnalysis;
import core.session.Session;

public class ChoiceStimuliPositionAnalysis extends UnstructuredAnalysis
{

	public ChoiceStimuliPositionAnalysis(Vector<? extends Session> data)
	{
		super(data);

		addMap(new ChoiceStimuliPositionAccuracyMap());

		addMap(new ChoiceStimuliPositionAvgRTMap());
	}

}

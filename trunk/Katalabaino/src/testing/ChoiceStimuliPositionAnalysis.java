package testing;

import java.util.Vector;

import core.Session;

public class ChoiceStimuliPositionAnalysis extends MultiSessionHDAnalysis
{

	public ChoiceStimuliPositionAnalysis(Vector<? extends Session> data)
	{
		super(data);

		addMap(new ChoiceStimuliPositionAccuracyMap());

		addMap(new ChoiceStimuliPositionAvgRTMap());
	}

}

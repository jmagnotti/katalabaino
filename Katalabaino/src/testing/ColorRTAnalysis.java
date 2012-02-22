package testing;

import java.util.Vector;

import core.Session;

public class ColorRTAnalysis extends MultiSessionHDAnalysis
{

	public ColorRTAnalysis(Vector<? extends Session> data)
	{
		super(data);
		
		addMap(new SampleChoiceColorAvgRTMap());
	}

}

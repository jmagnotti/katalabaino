package testing;

import java.util.Vector;

import core.analysis.UnstructuredAnalysis;
import core.session.Session;

public class ColorRTAnalysis extends UnstructuredAnalysis
{

	public ColorRTAnalysis(Vector<? extends Session> data)
	{
		super(data);
		
		addMap(new SampleChoiceColorAvgRTMap());
	}

}

package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.Trial;

class SegmentDistance extends MultiClassRule
{

	@Override
	public String getClassMembership(Trial trial)
	{
		String cls = "";

		String changeFrom, changeTo;

		changeFrom = trial.sampleStimuli.get(0).file;
		changeTo = trial.choiceStimuli.get(0).file;

		double deg1 = Integer.parseInt(changeFrom.substring(4, 5));
		double deg2 = Integer.parseInt(changeTo.substring(4, 5));

		double change = 22.5 * Math.abs(deg1 - deg2);
		cls = "" + change;

		if (change < 100) cls = "0" + cls;
		return "c_" + cls + ".";
	}

}

public class LineSegmentDistanceSplitter extends Splitter
{
	public LineSegmentDistanceSplitter()
	{
		super(new SegmentDistance());

	}
}

package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.Trial;

class SegmentDistance extends MultiClassRule {
	private int distanceType;

	public SegmentDistance(int type) {
		distanceType = type;
	}

	@Override
	public String getClassMembership(Trial trial) {
		String cls = "";

		String initial, terminal;

		initial = trial.sampleStimuli.get(0).file;
		if (distanceType == LineSegmentDistanceSplitter.CHANGE_DETECTION)
			terminal = trial.choiceStimuli.get(0).file;
		else
			terminal = trial.choiceStimuli.get(1).file;

		double deg1 = Integer.parseInt(initial.substring(4, 5));
		double deg2 = Integer.parseInt(terminal.substring(4, 5));

		double change = 22.5 * Math.abs(deg1 - deg2);
		cls = "" + change;

		if (change < 100)
			cls = "0" + cls;
		return "c_" + cls + ".";
	}

}

public class LineSegmentDistanceSplitter extends Splitter {

	public static final int TARGET_SEARCH = 1;
	public static final int CHANGE_DETECTION = 2;

	public LineSegmentDistanceSplitter(int type) {
		super(new SegmentDistance(type));
	}

}

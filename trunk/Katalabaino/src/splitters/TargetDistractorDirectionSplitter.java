package splitters;

import java.awt.geom.Point2D;
import java.util.Vector;

import core.Splitter;
import core.MultiClassRule;
import core.Trial;

class TDDirectionRule extends MultiClassRule {
	private Vector<Point2D.Float> locations;

	public TDDirectionRule() {
		locations = new Vector<Point2D.Float>();

		locations.add(new Point2D.Float(386f, 189.0f));
		locations.add(new Point2D.Float(428.5f, 262.6f));
		locations.add(new Point2D.Float(513.5f, 262.6f));
		locations.add(new Point2D.Float(556f, 189.0f));
		locations.add(new Point2D.Float(513.5f, 115.39f));
		locations.add(new Point2D.Float(428.5f, 115.39f));
		locations.add(new Point2D.Float(341.96f, 263.5f));
		locations.add(new Point2D.Float(471f, 338.0f));
		locations.add(new Point2D.Float(600.04f, 263.5f));
		locations.add(new Point2D.Float(600.04f, 114.5f));
		locations.add(new Point2D.Float(471f, 40.0f));
		locations.add(new Point2D.Float(341.96f, 114.5f));
	}

	@Override
	public String getClassMembership(Trial trial) {

		int target = trial.choiceStimuli.get(0).position;
		int c1 = trial.choiceStimuli.get(1).position;

		Point2D p1 = locations.get(target - 1);
		Point2D p2 = locations.get(c1 - 1);


		String cls = "";
		
		if (p1.getY() < p2.getY())
			cls = cls + "S";
		else if (p1.getY() > p2.getY())
			cls = cls + "N";

		if (p1.getX() < p2.getX())
			cls = cls + "W";
		else if (p1.getX() > p2.getX())
			cls = cls + "E";
		
					
		return "dir" + cls + ".";
	}

}

public class TargetDistractorDirectionSplitter extends Splitter {

	public TargetDistractorDirectionSplitter() {
		super(new TDDirectionRule());
	}
}

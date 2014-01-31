package splitters;

import java.awt.geom.Point2D;
import java.util.Vector;

import core.ComparisonRule;
import core.MultiClassRule;
import core.Splitter;
import core.Trial;

class YN_DistanceRule extends MultiClassRule {
	private Vector<Point2D.Float> locations;

	public YN_DistanceRule() {
		locations = new Vector<Point2D.Float>();
		locations.add(new Point2D.Float(435.5000f, 207.5000f));
		locations.add(new Point2D.Float(370.5000f, 272.5000f));
		locations.add(new Point2D.Float(305.5000f, 207.5000f));
		locations.add(new Point2D.Float(370.5000f, 142.5000f));
		locations.add(new Point2D.Float(494.1373f, 247.6722f));
		locations.add(new Point2D.Float(446.9121f, 312.6722f));
		locations.add(new Point2D.Float(370.5000f, 337.5000f));
		locations.add(new Point2D.Float(294.0879f, 312.6722f));
		locations.add(new Point2D.Float(246.8627f, 247.6722f));
		locations.add(new Point2D.Float(246.8627f, 167.3278f));
		locations.add(new Point2D.Float(294.0879f, 102.3278f));
		locations.add(new Point2D.Float(370.5000f, 77.5000f));
		locations.add(new Point2D.Float(446.9121f, 102.3278f));
		locations.add(new Point2D.Float(494.1373f, 167.3278f));

		classLabels = new Vector<String>();
		classLabels.add("L0");
		classLabels.add("L100");
		classLabels.add("L160");
		classLabels.add("L220");
		classLabels.add("LInf");

		rules = new Vector<ComparisonRule>();
		rules.add(new ComparisonRule(ComparisonRule.LESS_THAN, 1.0));
		rules.add(new ComparisonRule(ComparisonRule.LESS_THAN, 100.0));
		rules.add(new ComparisonRule(ComparisonRule.LESS_THAN, 160.0));
		rules.add(new ComparisonRule(ComparisonRule.LESS_THAN, 220));
		rules.add(new ComparisonRule(ComparisonRule.GT_OR_EQ, 220));
	}

	private double dev2(double a, double b) {
		return Math.pow(a - b, 2);
	}

	@Override
	public String getClassMembership(Trial trial) {

		int probe = trial.choiceStimuli.get(0).position;
		int lastPecked = trial.sampleResponses.lastElement().position;

		Point2D p1 = locations.get(probe - 1);
		Point2D p2 = locations.get(lastPecked - 1);

		double distance = Math.sqrt(dev2(p1.getX(), p2.getX())
				+ dev2(p1.getY(), p2.getY()));

		return arrayValidate(distance);
	}
}

public class YN_PeckDistanceSplitter extends Splitter {

	public YN_PeckDistanceSplitter() {
		super(new YN_DistanceRule());
	}

}

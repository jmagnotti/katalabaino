package splitters;

import java.text.DecimalFormat;
import java.util.Vector;

import calc.MahalanobisHelper;
import core.Trial;
import filters.ComparisonRule;

class MahalanobisDistanceRule extends MultiClassRule
{

	public MahalanobisDistanceRule(Vector<ComparisonRule> rules, Vector<String> labels)
	{
		this.rules = rules;
		this.classLabels = labels;
	}

	@Override
	public String getClassMembership(Trial trial)
	{

		double[][] data = MahalanobisHelper.GetSamplePositionData(trial);

		double distance = MahalanobisHelper.GetSinglePositionDistance(data,
				data[MahalanobisHelper.GetChangedStimulusIndex(trial)]);

		if (trial.sampleSetSize == 1) return "md_SSS1.";

		return arrayValidate(distance);
	}

}

public class ChangeItemMahalanobisSplitter extends Splitter
{
	public ChangeItemMahalanobisSplitter()
	{
		this(10);
	}

	public ChangeItemMahalanobisSplitter(int bins)
	{

		DecimalFormat df = new DecimalFormat("#.##");

		// we want to go from say 0 to 2 in however many increments
		double max = 1.7;
		double start = .5;
		double stepSize = (max - start) / bins;

		Vector<String> labels = new Vector<String>();
		Vector<ComparisonRule> rules = new Vector<ComparisonRule>();
		for (double i = start; i <= max; i += stepSize) {
			labels.add("LE" + df.format(i));
			rules.add(new ComparisonRule(ComparisonRule.LT_OR_EQ, (i)));
		}

		this.splitRule = new MahalanobisDistanceRule(rules, labels);
	}
}

package filters;

import core.ComparisonRule;
import core.Filter;
import core.Trial;

public class SampleSetSizeFilter extends Filter
{
	private ComparisonRule	rule;

	public SampleSetSizeFilter(ComparisonRule comparisonRule)
	{
		this.rule = comparisonRule;
	}

	public SampleSetSizeFilter(int val)
	{
		rule = new ComparisonRule(ComparisonRule.EQUAL_TO, val);
	}

	@Override
	protected boolean doAllow(Trial trial)
	{
		return rule.validate(trial.sampleSetSize);
	}

}

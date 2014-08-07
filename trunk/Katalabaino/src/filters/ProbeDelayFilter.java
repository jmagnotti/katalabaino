package filters;

import core.ComparisonRule;
import core.Filter;
import core.trial.Trial;

public class ProbeDelayFilter extends Filter
{
	private ComparisonRule	rule;

	public ProbeDelayFilter(double value)
	{
		this(new ComparisonRule(ComparisonRule.EQUAL_TO, value));
	}

	public ProbeDelayFilter(ComparisonRule cr)
	{
		this.rule = cr;
	}

	@Override
	protected boolean doAllow(Trial trial)
	{
		return rule.validate(trial.probeDelay);
	}
}

package filters;

import core.ComparisonRule;
import core.Filter;
import core.trial.Trial;

public class TrialFilter extends Filter
{
	private ComparisonRule	rule;

	public TrialFilter(ComparisonRule cr)
	{
		rule = cr;
	}

	@Override
	protected boolean doAllow(Trial trial)
	{
		return rule.validate(trial.trialNumber);
	}
}

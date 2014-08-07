package filters;

import core.ComparisonRule;
import core.Filter;
import core.trial.Trial;

public class SampleResponseFilter extends Filter
{
	private ComparisonRule	rule;

	public SampleResponseFilter(ComparisonRule rule)
	{
		this.rule = rule;
	}

	@Override
	protected boolean doAllow(Trial trial)
	{
		return rule.validate(trial.sampleResponses.size());
	}

}

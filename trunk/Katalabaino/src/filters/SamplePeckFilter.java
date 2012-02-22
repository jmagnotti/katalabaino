package filters;

import core.Trial;

public class SamplePeckFilter extends Filter
{
	private ComparisonRule	rule;

	public SamplePeckFilter(ComparisonRule rule)
	{
		this.rule = rule;
	}

	@Override
	protected boolean doAllow(Trial trial)
	{
		return rule.validate(trial.sampleResponses.size());
	}

}

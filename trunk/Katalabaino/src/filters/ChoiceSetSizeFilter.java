package filters;

import core.ComparisonRule;
import core.Filter;
import core.trial.Trial;

public class ChoiceSetSizeFilter extends Filter
{
	ComparisonRule	rule;

	public ChoiceSetSizeFilter(int allow)
	{
		rule = new ComparisonRule(ComparisonRule.EQUAL_TO, allow);
	}

	public ChoiceSetSizeFilter(ComparisonRule rule)
	{
		this.rule = rule;
	}

	@Override
	protected boolean doAllow(Trial trial)
	{
		return rule.validate(trial.choiceSetSize);
	}
}

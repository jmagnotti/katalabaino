package filters;

import core.ComparisonRule;
import core.Filter;
import core.Session;

public class SessionNumberFilter extends Filter
{
	private ComparisonRule	rule;

	public SessionNumberFilter(ComparisonRule rule)
	{
		super();
		this.rule = rule;
	}

	@Override
	protected boolean doAllow(Session session)
	{
		return rule.validate(Integer.parseInt(session.id));
	}
}

package filters;

import core.Session;

public class SessionIDFilter extends Filter
{
	private ComparisonRule	rule;

	public SessionIDFilter(ComparisonRule rule)
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

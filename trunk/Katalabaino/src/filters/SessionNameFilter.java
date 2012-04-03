package filters;

import core.Filter;
import core.Session;

public class SessionNameFilter extends Filter
{
	private String	match;

	public SessionNameFilter(String match)
	{
		this.match = match;
	}

	@Override
	protected boolean doAllow(Session session)
	{
		return session.sessionFile.contains(match);
	}

}

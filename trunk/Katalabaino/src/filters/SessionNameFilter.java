package filters;

import core.Filter;
import core.Session;

public class SessionNameFilter extends Filter
{
	private String	match;
	
	public static boolean CASE_INSENSITIVE = true;
	

	public SessionNameFilter(String match)
	{
		this.match = match;
	}

	@Override
	protected boolean doAllow(Session session)
	{
		if (CASE_INSENSITIVE)
			return session.sessionFile.toLowerCase().contains(match.toLowerCase());
		
		return session.sessionFile.contains(match);
	}

}

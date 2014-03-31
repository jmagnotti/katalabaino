package filters;

import java.util.Vector;

import core.Filter;
import core.Session;

public class ResultsFileFilter extends Filter
{
	public static final int	INCLUDE	= 0;
	public static final int	EXCLUDE	= 1;

	private int				rule;
	private Vector<String>	list;

	public ResultsFileFilter(int rule, String... items)
	{
		this.rule = rule;
		list = new Vector<String>();

		for (String s : items)
			list.add(s);
	}

	@Override
	protected boolean doAllow(Session session)
	{
		if (rule == ResultsFileFilter.INCLUDE) {
			return list.contains(session.resultsFile);
		}

		return !list.contains(session.resultsFile);

	}
}

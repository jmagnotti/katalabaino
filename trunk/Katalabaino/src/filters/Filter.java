package filters;

import core.Session;
import core.Trial;

public abstract class Filter
{
	protected Filter	child;
	private boolean		flipped;

	public Filter()
	{
		child = null;
		flipped = false;
	}

	public boolean doesAllow(Trial trial)
	{
		if (null == child) return doAllow(trial);

		return child.doesAllow(trial) && doAllow(trial);
	}

	public boolean doesAllow(Session session)
	{
		if (null == child) return flipped ? !doAllow(session) : doAllow(session);

		return child.doesAllow(session) && (flipped ? !doAllow(session) : doAllow(session));
	}

	public Filter flip()
	{
		flipped = !flipped;

		return this;
	}

	/**
	 * this should be overriden by children. default implementations are provided in case you only
	 * want to override one or the other
	 * 
	 * @param session
	 * @return True to include, False to exclude
	 */
	protected boolean doAllow(Session session)
	{
		return true;
	}

	/**
	 * this should be overriden by children. default implementations are provided in case you only
	 * want to override one or the other
	 * 
	 * @param trial
	 * @return True to include, False to exclude
	 */
	protected boolean doAllow(Trial trial)
	{
		return true;
	}

	public void addChild(Filter filter)
	{
		if (null != child)
			child.addChild(filter);
		else
			child = filter;
	}

	public void removeChild()
	{
		if (child != null) child.removeChild();
		child = null;
	}
}

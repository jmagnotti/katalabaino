package filters;

import core.Session;

public class CPFilter extends Filter
{
	private int				cpStatus;

	public static final int	CP_ON	= 1;
	public static final int	CP_OFF	= 0;

	public CPFilter(int cpStatus)
	{
		this.cpStatus = cpStatus;
	}

	@Override
	protected boolean doAllow(Session session)
	{
//		if (session.isIncorrectCorrectionsEnabled)
//			System.out.println("ON: " + session.id);
		
		if (cpStatus == CP_ON) {
			return session.isIncorrectCorrectionsEnabled;
		}

		return !session.isIncorrectCorrectionsEnabled;
	}

}

package mappers;

import java.util.Vector;

import core.Session;
import core.Trial;

public class SessionInfoMap extends Mapper
{
	public SessionInfoMap()
	{
//		super(" subject\tid");
		 super(" session");
//		 super(" results\tsession");
//		super(" cmt\tsubj\tid\tsfile");
	}

	public SessionInfoMap(String... keys)
	{
		super(implode(keys));
	}

	// this doesn't really belong in this class...
	private static String implode(String[] keys)
	{
		String result = " " + keys[0];

		for (int i = 1; i < keys.length; i++)
			result = "\t" + keys[i];

		return result;
	}

	@Override
	public void nextSession(Session session)
	{
		resultString = new Vector<String>();

//		 resultString.add(session.comment);
//		resultString.add(session.subject);
//		resultString.add(session.id);
//		 resultString.add(session.resultsFile);
		 resultString.add(session.sessionFile);
	}

	@Override
	public boolean allowSplits()
	{
		return false;
	}

	@Override
	public boolean needsTrials()
	{
		return false;
	}

	@Override
	public void nextTrial(Trial trial)
	{}

	@Override
	public Vector<String> cleanUp()
	{
		return resultString;
	}

}

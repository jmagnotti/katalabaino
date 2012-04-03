package mappers;

import java.util.Vector;

import core.Mapper;
import core.Session;
import core.Trial;

public class CorrectionProcedureStatusMap extends Mapper
{
	public CorrectionProcedureStatusMap()
	{
		super("CP");
	}

	@Override
	public void nextSession(Session session)
	{
		resultString = new Vector<String>();
		resultString.add(session.isIncorrectCorrectionsEnabled ? "1" : "0");
	}

	@Override
	public void nextTrial(Trial trial)
	{}

	@Override
	public Vector<String> cleanUp()
	{
		return resultString;
	}

	@Override
	public boolean needsTrials()
	{
		return false;
	}

	@Override
	public boolean allowSplits()
	{
		return false;
	}
}

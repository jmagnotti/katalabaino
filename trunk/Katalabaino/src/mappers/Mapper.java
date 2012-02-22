package mappers;

import java.util.Vector;

import core.Analysis;
import core.Session;
import core.Trial;

public abstract class Mapper
{
	protected Vector<String>	resultString;
	private final String		name;

	public Mapper(String name)
	{
		this.name = name;

		resultString = new Vector<String>();
	}

	public abstract void nextSession(Session session);

	public abstract void nextTrial(Trial trial);

	public abstract Vector<String> cleanUp();

	public boolean allowSplits()
	{
		return true;
	}

	public boolean needsTrials()
	{
		return true;
	}

	@Override
	public String toString()
	{
		return name.replace("\t", Analysis.SPACE_DELIMITER);
	}

	public String toString(String prefix)
	{
		return prefix
				+ this.toString().replace(Analysis.SPACE_DELIMITER,
						Analysis.SPACE_DELIMITER + prefix);
	}

}

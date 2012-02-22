package mappers;

import java.util.Vector;

import core.Session;
import core.Trial;

public class CountCorrectMap extends Mapper
{
	private double	correct;

	public CountCorrectMap()
	{
		super("corr");

		correct = 0.0;
	}

	@Override
	public void nextSession(Session session)
	{
		correct = 0.0;
	}

	@Override
	public void nextTrial(Trial trial)
	{
		correct += trial.getCorrectAsInt();
	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString = new Vector<String>();

		resultString.add("" + correct);

		correct = 0.0;

		return resultString;
	}
}

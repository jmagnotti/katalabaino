package mappers;

import java.util.Vector;

import core.Mapper;
import core.Session;
import core.Trial;

public class PercentCorrectMap extends Mapper
{
	private double	count, correct;

	public PercentCorrectMap()
	{
		super("accuracy");
		count = correct = 0.0;
	}

	@Override
	public void nextSession(Session session)
	{
		count = correct = 0.0;
	}

	@Override
	public void nextTrial(Trial trial)
	{
		count++;
		correct += trial.getCorrectAsInt();
	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString = new Vector<String>();
		resultString.add("" + (100.0 * correct / count));

		count = correct = 0.0;

		return resultString;
	}
}

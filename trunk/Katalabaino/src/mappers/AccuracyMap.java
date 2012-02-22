package mappers;

import java.util.Vector;

import core.Session;
import core.Trial;

public class AccuracyMap extends Mapper
{
	public AccuracyMap()
	{
		super("acc");

		count = correct = 0.0;
	}

	private double	count, correct;

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

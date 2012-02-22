package mappers;

import java.util.Vector;

import core.Session;
import core.Trial;

public class IncorrectCorrectionsMap extends Mapper
{
	private double	totalCorrections, trialCount, atLeastOne;

	public IncorrectCorrectionsMap()
	{
		super("totIC\tavgIC\tpropIC");

		totalCorrections = trialCount = atLeastOne = 0.0;
	}

	@Override
	public void nextSession(Session session)
	{
		totalCorrections = trialCount = atLeastOne = 0.0;
	}

	@Override
	public void nextTrial(Trial trial)
	{
		if (trial.incorrectCorrections > 0) {
			totalCorrections += trial.incorrectCorrections;
			atLeastOne++;
			trialCount++;
		}

	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString = new Vector<String>();

		resultString.add("" + totalCorrections + "\t" + (totalCorrections / trialCount) + "\t"
				+ atLeastOne);

		totalCorrections = trialCount = atLeastOne = 0.0;

		return resultString;
	}
}

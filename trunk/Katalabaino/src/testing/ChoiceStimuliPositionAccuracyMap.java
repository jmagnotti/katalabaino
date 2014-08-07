package testing;

import java.util.Vector;

import core.Mapper;
import core.session.Session;
import core.trial.Trial;

public class ChoiceStimuliPositionAccuracyMap extends Mapper
{
	private Vector<Vector<Double>>	itemPositionCounts, itemPositionCorrect;

	public ChoiceStimuliPositionAccuracyMap()
	{
		super("posAcc");

		itemPositionCorrect = new Vector<Vector<Double>>();
		itemPositionCounts = new Vector<Vector<Double>>();

		for (int i = 0; i < 24; i++) {
			itemPositionCorrect.add(new Vector<Double>());
			itemPositionCounts.add(new Vector<Double>());
			for (int j = 0; j < 24; j++) {
				itemPositionCorrect.get(i).add(0.0);
				itemPositionCounts.get(i).add(0.0);
			}
		}
	}

	@Override
	public void nextSession(Session session)
	{}

	@Override
	public void nextTrial(Trial trial)
	{
		double oldCount = itemPositionCounts.get(trial.choiceStimuli.get(0).position - 1).get(
				trial.choiceStimuli.get(1).position - 1);

		itemPositionCounts.get(trial.choiceStimuli.get(0).position - 1).set(
				trial.choiceStimuli.get(1).position - 1, oldCount + 1);

		if (trial.isCorrect()) {
			oldCount = itemPositionCorrect.get(trial.choiceStimuli.get(0).position - 1).get(
					trial.choiceStimuli.get(1).position - 1);

			itemPositionCorrect.get(trial.choiceStimuli.get(0).position - 1).set(
					trial.choiceStimuli.get(1).position - 1, oldCount + 1);
		}
	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString.clear();

		String string = "";

		for (int i = 0; i < itemPositionCorrect.size(); i++)
			string = string + ("\tnc-loc." + (i + 1));

		resultString.add(string);

		string = "";
		for (int i = 0; i < itemPositionCounts.size(); i++) {

			string = "c-loc." + (i + 1);
			for (int j = 0; j < itemPositionCorrect.size(); j++) {
				string = string
						+ "\t"
						+ (100.0 * itemPositionCorrect.get(i).get(j) / +itemPositionCounts.get(i)
								.get(j));

			}

			resultString.add(string);
		}

		itemPositionCorrect.clear();
		itemPositionCounts.clear();

		return resultString;
	}
}

package testing;

import java.util.Vector;

import core.Mapper;
import core.Session;
import core.Trial;

public class ChoiceStimuliPositionAvgRTMap extends Mapper
{

	private Vector<Vector<Double>>	itemPositionCount, itemPositionRT;

	public ChoiceStimuliPositionAvgRTMap()
	{
		super("avgRT");

		itemPositionRT = new Vector<Vector<Double>>();
		itemPositionCount = new Vector<Vector<Double>>();

		for (int i = 0; i < 24; i++) {
			itemPositionRT.add(new Vector<Double>());
			itemPositionCount.add(new Vector<Double>());
			for (int j = 0; j < 24; j++) {
				itemPositionRT.get(i).add(0.0);
				itemPositionCount.get(i).add(0.0);
			}
		}
	}

	@Override
	public void nextSession(Session session)
	{}

	@Override
	public void nextTrial(Trial trial)
	{
		double oldCount = itemPositionCount.get(trial.choiceStimuli.get(0).position - 1).get(
				trial.choiceStimuli.get(1).position - 1);

		itemPositionCount.get(trial.choiceStimuli.get(0).position - 1).set(
				trial.choiceStimuli.get(1).position - 1, oldCount + 1);

		oldCount = itemPositionRT.get(trial.choiceStimuli.get(0).position - 1).get(
				trial.choiceStimuli.get(1).position - 1);

		itemPositionRT.get(trial.choiceStimuli.get(0).position - 1).set(
				trial.choiceStimuli.get(1).position - 1, oldCount + trial.responseTime);
	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString.clear();

		String string = "";

		for (int i = 0; i < itemPositionRT.size(); i++)
			string = string + ("\tnc-loc." + (i + 1));

		resultString.add(string);

		string = "";
		for (int i = 0; i < itemPositionCount.size(); i++) {

			string = "c-loc." + (i + 1);
			for (int j = 0; j < itemPositionRT.size(); j++) {
				string = string + "\t"
						+ (itemPositionRT.get(i).get(j) / +itemPositionCount.get(i).get(j));

			}

			resultString.add(string);
		}

		itemPositionRT.clear();
		itemPositionCount.clear();

		return resultString;
	}

}

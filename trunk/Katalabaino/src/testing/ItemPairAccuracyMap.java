package testing;

import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import mappers.Mapper;
import core.Colors;
import core.Session;
import core.Trial;

public class ItemPairAccuracyMap extends Mapper
{
	private HashMap<String, HashMap<String, Double>>	itemCounts, itemCorrect;
	private Vector<String>								imageNames;

	public ItemPairAccuracyMap()
	{
		super("itemAcc");

		itemCorrect = new HashMap<String, HashMap<String, Double>>();
		itemCounts = new HashMap<String, HashMap<String, Double>>();

		imageNames = new Vector<String>();
	}

	@Override
	public void nextSession(Session session)
	{}

	@Override
	public void nextTrial(Trial trial)
	{
		double previousCount = 0;
		String sample, probe;
		sample = Colors.GetInstance().colorIDToLabel.get(Colors.GetInstance().fileToColorID(
				trial.sampleStimuli.get(0).file)); // .replaceAll(".tga", "");
		probe = Colors.GetInstance().colorIDToLabel.get(Colors.GetInstance().fileToColorID(
				trial.choiceStimuli.get(0).file)); // .replaceAll(".tga", "");

		// probe = trial.choiceStimuli.get(0).file.replaceAll(".tga", "");

		if (itemCounts.containsKey(sample)) {
			if (itemCounts.get(sample).containsKey(probe))
				previousCount = itemCounts.get(sample).get(probe);
			else {
				itemCounts.get(sample).put(probe, 1.0);
				itemCorrect.get(sample).put(probe, 0.0);
			}
		}
		else {
			itemCounts.put(sample, new HashMap<String, Double>());
			itemCorrect.put(sample, new HashMap<String, Double>());
			itemCorrect.get(sample).put(probe, 0.0);

			// we'll get the probe when it is a sample
			imageNames.add(sample);
		}

		// this will set to 1 if the key is new
		itemCounts.get(sample).put(probe, previousCount + 1);

		if (trial.isCorrect()) {
			previousCount = itemCorrect.get(sample).get(probe);
			itemCorrect.get(sample).put(probe, previousCount + 1);
		}
	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString.clear();

		String string = "";

		Collections.sort(imageNames);

		for (String name : imageNames)
			string = string + "\tProbe." + name;

		resultString.add(string);

		string = "";
		String sample, probe;
		for (int i = 0; i < imageNames.size(); i++) {

			sample = imageNames.get(i);
			string = "Sample." + sample;

			for (int j = 0; j < imageNames.size(); j++) {
				probe = imageNames.get(j);

				if (itemCounts.containsKey(sample) && itemCounts.get(sample).containsKey(probe)) {
					string = string + "\t"
					 + (100.0 * itemCorrect.get(sample).get(probe) / itemCounts.get(sample)
					 .get(probe));
//							+ (itemCounts.get(sample).get(probe));
				}
				else {
					string = string + "\t.";
				}

			}

			resultString.add(string);
		}

		itemCorrect.clear();
		itemCounts.clear();

		return resultString;
	}
}

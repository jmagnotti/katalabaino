package filters;

import java.util.HashMap;

import core.ComparisonRule;
import core.Filter;
import core.trial.Trial;

public class TrialConfigurationCountFilter extends Filter
{
	private HashMap<String, HashMap<String, Double>>	itemCounts;
	private ComparisonRule								rule;

	public static final String							REMOVE_EXTENSION	= ".tga";

	public TrialConfigurationCountFilter(ComparisonRule cr)
	{
		rule = cr;
		itemCounts = new HashMap<String, HashMap<String, Double>>();
	}

	@Override
	protected boolean doAllow(Trial trial)
	{
		double previousCount = 0;
		String sample, probe;
		sample = trial.sampleStimuli.get(0).file.replaceAll(REMOVE_EXTENSION, "");
		probe = trial.choiceStimuli.get(0).file.replaceAll(REMOVE_EXTENSION, "");

		if (itemCounts.containsKey(sample)) {
			if (itemCounts.get(sample).containsKey(probe))
				previousCount = itemCounts.get(sample).get(probe);
		}
		else {
			itemCounts.put(sample, new HashMap<String, Double>());
		}

		itemCounts.get(sample).put(probe, previousCount + 1);

		return rule.validate(itemCounts.get(sample).get(probe));
	}
}

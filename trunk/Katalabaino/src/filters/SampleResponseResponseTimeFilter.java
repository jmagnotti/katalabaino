package filters;

import core.ComparisonRule;
import core.Filter;
import core.Session;
import core.Trial;

public class SampleResponseResponseTimeFilter extends Filter {
	public static final int FOUR_SIGMA = 1;

	private boolean usingRule;

	private ComparisonRule rule;

	public SampleResponseResponseTimeFilter(int specialCase) {
		usingRule = true;
	}

	@Override
	protected boolean doAllow(Session session) {

		if (usingRule) {
			double x, x2;
			x = x2 = 0.0;

			for (Trial t : session.trials) {
				if (!t.sampleResponses.isEmpty()) {
					// System.out.println(session.id + ":"+t.trialNumber);
					x += t.sampleResponses.lastElement().responseTime;

					x2 += (t.sampleResponses.lastElement().responseTime * t.sampleResponses
							.lastElement().responseTime);
				} else {
					System.err.println("No FR!!! Session " + session.id
							+ " Trial #" + t.trialNumber);
				}
			}

			double eX = x / session.trials.size();
			double eX2 = x2 / session.trials.size();

			double var = (session.trials.size() / (session.trials.size() - 1))
					* (eX2 - eX * eX);

			rule = new ComparisonRule(ComparisonRule.INCLUSIVE, eX - 4
					* Math.sqrt(var), eX + 4 * Math.sqrt(var));

			// System.out.println("M: " + eX + ", SD: " + Math.sqrt(var));
		}

		return true;
	}

	public SampleResponseResponseTimeFilter(ComparisonRule comparisonRule) {
		usingRule = false;
		rule = comparisonRule;
	}

	@Override
	protected boolean doAllow(Trial trial) {
		if (trial.sampleResponses.isEmpty()) {
			// System.err.println("No FR!! Trial Number: " + trial.trialNumber);
			return true;
		}
		return rule.validate(trial.sampleResponses.lastElement().responseTime);
	}

}

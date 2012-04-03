package filters;

import core.ComparisonRule;
import core.Filter;
import core.Session;
import core.Trial;

public class ResponseTimeFilter extends Filter
{
	public static final int	FOUR_SIGMA	= 1;

	private boolean			usingRule;

	private ComparisonRule	rule;

	public ResponseTimeFilter(int specialCase)
	{
		usingRule = true;
	}

	@Override
	protected boolean doAllow(Session session)
	{

		if (usingRule) {
			double x, x2;
			x = x2 = 0.0;

			for (Trial t : session.trials) {
				x += t.responseTime;

				x2 += (t.responseTime * t.responseTime);
			}

			double eX = x / session.trials.size();
			double eX2 = x2 / session.trials.size();

			double var = (session.trials.size() / (session.trials.size() - 1)) * (eX2 - eX * eX);

			rule = new ComparisonRule(ComparisonRule.INCLUSIVE, eX - 4 * Math.sqrt(var), eX + 4
					* Math.sqrt(var));

			// System.out.println("M: " + eX + ", SD: " + Math.sqrt(var));
		}

		return true;
	}

	public ResponseTimeFilter(ComparisonRule comparisonRule)
	{
		usingRule = false;
		rule = comparisonRule;
	}

	@Override
	protected boolean doAllow(Trial trial)
	{
		return rule.validate(trial.responseTime);
	}

}

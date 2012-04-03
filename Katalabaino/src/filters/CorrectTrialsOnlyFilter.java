package filters;

import core.Filter;
import core.Trial;

public class CorrectTrialsOnlyFilter extends Filter
{
	public CorrectTrialsOnlyFilter()
	{}

	@Override
	protected boolean doAllow(Trial trial)
	{
		return trial.isCorrect();
	}
}

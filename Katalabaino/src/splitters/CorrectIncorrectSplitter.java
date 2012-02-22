package splitters;

import core.Trial;

class CorrectIncorrectRule extends MultiClassRule
{

	@Override
	public String getClassMembership(Trial trial)
	{
		return trial.isCorrect() ? "cor." : "inc.";
	}

}

public class CorrectIncorrectSplitter extends Splitter
{
	public CorrectIncorrectSplitter()
	{
		super(new CorrectIncorrectRule());
	}
}

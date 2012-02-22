package splitters;

import core.Trial;

class ItemRule extends MultiClassRule
{

	@Override
	public String getClassMembership(Trial trial)
	{
		return trial.sampleStimuli.get(0).file.replaceAll(".tga", "") + ":"
				+ trial.choiceStimuli.get(0).file.replaceAll(".tga", "");
	}

}

public class ItemConfigurationSplitter extends Splitter
{
	public ItemConfigurationSplitter()
	{
		super(new ItemRule());
	}
}

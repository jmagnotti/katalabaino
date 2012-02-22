package splitters;

import core.Trial;

class ConfigurationSplitRule extends MultiClassRule
{
	private final String	prefix;

	public ConfigurationSplitRule(String prefix)
	{
		this.prefix = prefix;
	}

	@Override
	public String getClassMembership(Trial trial)
	{
		return prefix + trial.configuration + ".";
	}

}

public class ConfigurationSplitter extends Splitter
{
	public ConfigurationSplitter()
	{
		super(new ConfigurationSplitRule("cf"));
	}

	public ConfigurationSplitter(String prefix)
	{
		super(new ConfigurationSplitRule(prefix));
	}
}

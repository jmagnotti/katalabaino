package core;

import java.util.Vector;


/**
 * These rules are like {@link ComparisonRule} but are multi-class.
 * 
 * The basic idea is that there are several classes to match against, and returns class membership
 * rather than a simple true/false
 */
public abstract class MultiClassRule
{

	// these aren't always used, but thinking they may come in handy later, say if we need to
	// enumerate all possible classes or something
	protected Vector<ComparisonRule>	rules;
	protected Vector<String>			classLabels;

	public abstract String getClassMembership(Trial trial);

	/**
	 * helper function to loop through all the classes. Consider it a nice benefit if you put your
	 * rules/labels into the provided vectors
	 * 
	 * @return
	 */
	protected String arrayValidate(double value)
	{
		String classLabel = "";
		for (int i = 0; i < rules.size() && classLabel.equals(""); i++) {
			if (rules.get(i).validate(value)) {
				classLabel = classLabels.get(i);
			}
		}

		if (classLabel.equals("")) classLabel = "NA";

		return classLabel + ".";

	}

}

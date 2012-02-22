package filters;

public class ComparisonRule
{
	public static final int	ANY				= -99;

	public static final int	LESS_THAN		= -1;
	public static final int	LT_OR_EQ		= -10;

	public static final int	EQUAL_TO		= 111;
	public static final int	NOT_EQUAL_TO	= -111;

	public static final int	GREATER_THAN	= 1;
	public static final int	GT_OR_EQ		= 10;

	public static final int	INCLUSIVE		= 100;
	public static final int	EXCLUSIVE		= 200;

	private int				rule;
	private double			value, upperBound, lowerBound;

	public ComparisonRule(int rule, double value)
	{
		this.rule = rule;
		this.value = value;

		upperBound = Double.POSITIVE_INFINITY;
		lowerBound = Double.NEGATIVE_INFINITY;
	}

	/**
	 * Explicit constructor for interval rules. You could make a MultiClassRule instead of using
	 * bounds, but this feels pretty natural, although the non-nested nature of the constructors is
	 * not that nice.
	 * 
	 * @param rule
	 *            Should really only be one of INCLUSIVE or EXCLUSIVE
	 * @param lowerBound
	 * @param upperBound
	 */
	public ComparisonRule(int rule, double lowerBound, double upperBound)
	{
		this.rule = rule;
		value = Double.NaN;

		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public boolean validate(double test)
	{
		// System.out.println("Testing: " + test +"\tLB: " + this.lowerBound + "\tUB:" +
		// this.upperBound + "\tRule: " + this.rule);
		switch (rule) {
			case LESS_THAN:
				return test < value;
			case LT_OR_EQ:
				return test <= value;
			case EQUAL_TO:
				return test == value;
			case NOT_EQUAL_TO:
				return test != value;
			case GREATER_THAN:
				return test > value;
			case GT_OR_EQ:
				return test >= value;

			case INCLUSIVE:
				return ((test >= lowerBound) && (test <= upperBound));
			case EXCLUSIVE:
				return (test < lowerBound) || (test > upperBound);

			case ANY:
			default:
				return true;
		}
	}

}

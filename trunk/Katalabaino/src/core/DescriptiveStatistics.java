package core;

import java.util.Arrays;

public class DescriptiveStatistics
{
	private static double GetMedian(double... values)
	{
		double median;
		int len = values.length;
		Arrays.sort(values);

		if (values.length % 2 == 0)
			median = .5 * (values[len / 2] + values[len / 2 - 1]);
		else
			median = values[len / 2];

		return median;
	}

	public static double GetMedian(boolean inPlace, double... values)
	{
		if (inPlace) return GetMedian(values);

		double[] copy = Arrays.copyOf(values, values.length);

		return GetMedian(copy);
	}

	public static void main(String[] args)
	{
		double vals[] = { 2, 3, 1, 4 };

		System.out.println("median: " + GetMedian(true, vals));

		for (double d : vals)
			System.out.println(d);

	}

}

package testing;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuickScript
{
	public static double[] forcedChoice_ECJ(double n, double c)
	{
		double[] res = new double[2];

		if (c >= n) return new double[] { 100, 0 };

		double g = (n - c) / n;
		g *= g;

		double h = 1 - g;

		res[0] = 100 * h;
		res[1] = 50 * g;

		return res;
	}

	public static double[] forcedChoice_MAG(double n, double c)
	{
		double[] res = new double[2];

		if (c >= n) return new double[] { 100, 0 };

		double g = (n - c) / n;
		g *= ((n - 1) - c) / (n - 1);

		double h = 1 - g;

		res[0] = 100 * h;
		res[1] = 50 * g;

		return res;
	}

	public static void main(String[] args)
	{

		for (int c = 1; c <= 5; c++) {
			for (String s : new String[] { "acc" }) { // , "g" }) {
				System.out.print("\t" + c + s);
			}
		}

		System.out.println();

		for (int n = 2; n <= 12; n++) {
			System.out.print(n);
			for (int c = 1; c <= 5; c += 1) {
				double[] res = forcedChoice_MAG(n, c);
				// System.out.print("\t" + res[0] + "\t" + res[1]);
				System.out.print("\t" + (res[0] + res[1]));
			}

			System.out.println();
		}
	}
}

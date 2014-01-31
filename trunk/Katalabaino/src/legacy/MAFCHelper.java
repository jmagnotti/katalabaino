package legacy;

import java.util.HashMap;

public class MAFCHelper
{
	public HashMap<Double, HashMap<Double, Double>>	percentCorrectToDprime;

	public MAFCHelper()
	{
		percentCorrectToDprime = new HashMap<Double, HashMap<Double, Double>>();
		for (double i = 2; i <= 8; i++)
			percentCorrectToDprime.put(i, new HashMap<Double, Double>());

		double index = 2.0;

		percentCorrectToDprime.get(index).put((double) 50, 0.0);
		percentCorrectToDprime.get(index).put((double) 55, .18);
		percentCorrectToDprime.get(index).put((double) 60, .36);
		percentCorrectToDprime.get(index).put((double) 65, .54);
		percentCorrectToDprime.get(index).put((double) 70, .74);
		percentCorrectToDprime.get(index).put((double) 75, .95);
		percentCorrectToDprime.get(index).put((double) 80, 1.19);
		percentCorrectToDprime.get(index).put((double) 85, 1.47);
		percentCorrectToDprime.get(index).put((double) 90, 1.81);
		percentCorrectToDprime.get(index).put((double) 95, 2.33);
		percentCorrectToDprime.get(index).put((double) 99, 3.29);
		percentCorrectToDprime.get(index).put((double) 100, 3.29);

		index = 3.0;
		percentCorrectToDprime.get(index).put((double) 45, .39);
		percentCorrectToDprime.get(index).put((double) 50, .56);
		percentCorrectToDprime.get(index).put((double) 55, .72);
		percentCorrectToDprime.get(index).put((double) 60, .89);
		percentCorrectToDprime.get(index).put((double) 65, 1.06);
		percentCorrectToDprime.get(index).put((double) 70, 1.24);
		percentCorrectToDprime.get(index).put((double) 75, 1.43);
		percentCorrectToDprime.get(index).put((double) 80, 1.65);
		percentCorrectToDprime.get(index).put((double) 85, 1.91);
		percentCorrectToDprime.get(index).put((double) 90, 2.23);
		percentCorrectToDprime.get(index).put((double) 95, 2.71);
		percentCorrectToDprime.get(index).put((double) 99, 3.62);
		percentCorrectToDprime.get(index).put((double) 100, 3.62);

		index = 4.0;
		percentCorrectToDprime.get(index).put((double) 25, 0.0);
		percentCorrectToDprime.get(index).put((double) 30, .19);
		percentCorrectToDprime.get(index).put((double) 40, .52);
		percentCorrectToDprime.get(index).put((double) 45, .68);
		percentCorrectToDprime.get(index).put((double) 50, .84);
		percentCorrectToDprime.get(index).put((double) 55, .99);
		percentCorrectToDprime.get(index).put((double) 60, 1.15);
		percentCorrectToDprime.get(index).put((double) 65, 1.32);
		percentCorrectToDprime.get(index).put((double) 70, 1.49);
		percentCorrectToDprime.get(index).put((double) 75, 1.68);
		percentCorrectToDprime.get(index).put((double) 80, 1.89);
		percentCorrectToDprime.get(index).put((double) 85, 2.14);
		percentCorrectToDprime.get(index).put((double) 90, 2.45);
		percentCorrectToDprime.get(index).put((double) 95, 2.92);
		percentCorrectToDprime.get(index).put((double) 99, 3.8);
		percentCorrectToDprime.get(index).put((double) 100, 3.8);

		index = 5.0;
		percentCorrectToDprime.get(index).put((double) 20, 0.0);
		percentCorrectToDprime.get(index).put((double) 30, .38);
		percentCorrectToDprime.get(index).put((double) 40, .71);
		percentCorrectToDprime.get(index).put((double) 45, .87);
		percentCorrectToDprime.get(index).put((double) 50, 1.02);
		percentCorrectToDprime.get(index).put((double) 55, 1.17);
		percentCorrectToDprime.get(index).put((double) 60, 1.33);
		percentCorrectToDprime.get(index).put((double) 65, 1.49);
		percentCorrectToDprime.get(index).put((double) 75, 1.85);
		percentCorrectToDprime.get(index).put((double) 80, 2.05);
		percentCorrectToDprime.get(index).put((double) 85, 2.29);
		percentCorrectToDprime.get(index).put((double) 90, 2.60);

		index = 6.0;
		percentCorrectToDprime.get(index).put((double) 15, -.08);
		percentCorrectToDprime.get(index).put((double) 20, .15);
		percentCorrectToDprime.get(index).put((double) 25, .35);
		percentCorrectToDprime.get(index).put((double) 30, .53);
		percentCorrectToDprime.get(index).put((double) 35, .69);
		percentCorrectToDprime.get(index).put((double) 40, .85);
		percentCorrectToDprime.get(index).put((double) 45, 1.0);
		percentCorrectToDprime.get(index).put((double) 50, 1.15);
		percentCorrectToDprime.get(index).put((double) 55, 1.3);
		percentCorrectToDprime.get(index).put((double) 60, 1.46);
		percentCorrectToDprime.get(index).put((double) 65, 1.62);
		percentCorrectToDprime.get(index).put((double) 70, 1.79);
		percentCorrectToDprime.get(index).put((double) 75, 1.97);
		percentCorrectToDprime.get(index).put((double) 80, 2.17);
		percentCorrectToDprime.get(index).put((double) 85, 2.41);

		index = 8.0;
		percentCorrectToDprime.get(index).put((double) 15, .13);
		percentCorrectToDprime.get(index).put((double) 20, .36);
		percentCorrectToDprime.get(index).put((double) 25, .55);
		percentCorrectToDprime.get(index).put((double) 30, .73);
		percentCorrectToDprime.get(index).put((double) 35, .89);
		percentCorrectToDprime.get(index).put((double) 40, 1.04);
		percentCorrectToDprime.get(index).put((double) 45, 1.19);
		percentCorrectToDprime.get(index).put((double) 50, 1.34);
		percentCorrectToDprime.get(index).put((double) 55, 1.49);
		percentCorrectToDprime.get(index).put((double) 75, 2.14);
	}

	public static void main(String[] args)
	{
		double[] pc = new double[] { 40, 60, 65, 65, 70, 75, 75, 80, 80, 80, 85, 85, 85, 85, 90,
				90, 90, 90, 90, 90, 90, 90, 95, 95, 95, 95, 95, 95, 95, 100, 100, 100 };
		MAFCHelper mafch = new MAFCHelper();
		for (Double d : pc)
			System.out.println(mafch.percentCorrectToDprime.get(4.0).get(d));

	}
}

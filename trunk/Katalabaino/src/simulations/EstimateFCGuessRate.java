package simulations;

import java.util.Random;

public class EstimateFCGuessRate
{
	public static Random	rand	= null;

	public static void sampleWithoutReplacement(int[] population, int[] sample, int n)
	{
		if (rand == null) rand = new Random();

		int size = population.length;

		int i = 0;
		while (i < n) {
			int s = rand.nextInt(size);
			size--;
			int t = population[size];
			sample[i] = population[s];
			population[s] = t;
			population[size] = sample[i];
			i++;
		}
	}

	private static double getTrueHit(double k, double m)
	{
		return 1 - ((m - k) / m);
	}

	private static double getGuessCorrect(double k, double m, double p)
	{
		double miss = 1 - getTrueHit(k, m);
		double guess = 0.0;
		
		
		
		return guess;
	}
	
	public static void main(String[] args)
	{
		simulate();
	}
	

	public static void fullEstimation()
	{
		double k = 3, know = 0.0, guessCorr = 0.0;
		for (int m = 4; m <= 8; m += 2) {
			for (int p = 2; p <= 5; p++) {
				know = getTrueHit(k, m);
				guessCorr = getGuessCorrect(k, m, p);

			}
		}
	}

	public static void simulate()
	{
		int k = 3;

		for (Integer displaySize : new int[] { 4, 6, 8 }) {
			for (int choiceSize = 1; choiceSize <= displaySize; choiceSize++) {

				double dontEncode = 0;

				int[] counts = new int[Math.min(k + 1, choiceSize + 1)];

				for (int i = 0; i < counts.length; i++)
					counts[i] = 0;

				int[] memoryArray = new int[k];
				int[] sampleArray = new int[displaySize];

				for (int i = 0; i < displaySize; i++)
					sampleArray[i] = i + 1;

				double len = 10000000.0;
				for (int i = 0; i < len; i++) {

					sampleWithoutReplacement(sampleArray, memoryArray, k);

					int count = 0;
					boolean correct = false;
					for (int m = 0; m < k; m++) {
						if (memoryArray[m] <= choiceSize) {
							count++;
						}
						correct = (correct || memoryArray[m] == 1);
					}

					if (!correct) {
						counts[count]++;
						dontEncode++;
					}
				}

				System.out.println("DS: " + displaySize + "\tCDS: " + choiceSize);
				for (int i = 0; i < counts.length; i++)
					System.out.print(i + "\t");
				System.out.println();
				for (int i = 0; i < counts.length; i++)
					System.out.print((counts[i] / dontEncode) + "\t");

				System.out.println();
				for (int i = 0; i < counts.length; i++)
					System.out.print((counts[i] / len) + "\t");
				System.out.println();
			}
		}
	}

}

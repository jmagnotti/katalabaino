package legacy;

public class DissimilarityReport extends Report
{
	private double	stimulusCounts[][];
	private double	stimulusCorrect[][];

	public DissimilarityReport()
	{
		stimulusCounts = new double[LegacySession.colors.length][LegacySession.colors.length];
		stimulusCorrect = new double[LegacySession.colors.length][LegacySession.colors.length];

		for (int i = 0; i < stimulusCorrect.length; i++) {
			for (int j = 0; j < stimulusCorrect[i].length; j++) {
				stimulusCounts[i][j] = 0.0;
				stimulusCorrect[i][j] = 0.0;
			}
		}
	}

	@Override
	public void preSession(LegacySession session)
	{
		// we are doing this across sessions, so don't worry about it
	}

	@Override
	public void analyzeTrial(Trial trial)
	{
		if (trial.sampleDisplaySize == 4) {
			stimulusCorrect[trial.correctColor][trial.changeFromColor] += trial.isCorrect() ? 1 : 0;
			stimulusCounts[trial.correctColor][trial.changeFromColor]++;
		}
	}

	@Override
	public void postSession()
	{}

	@Override
	public void allFinished()
	{

		// we want to map the colors so they are sorted by luminance values
		int newOrder[] = { LegacySession.ColorToInt("navy"), LegacySession.ColorToInt("blue"),
				LegacySession.ColorToInt("green"), LegacySession.ColorToInt("red"), LegacySession.ColorToInt("gray"),
				LegacySession.ColorToInt("pink"), LegacySession.ColorToInt("yellowGreen"),
				LegacySession.ColorToInt("orange"), LegacySession.ColorToInt("lime"),
				LegacySession.ColorToInt("aqua"), LegacySession.ColorToInt("yellow"),
				LegacySession.ColorToInt("white") };

//		int newOrder[] = {0, 1, 2, 3,4, 5, 6,7, 8, 9, 10, 11};
		
		for (int i = 0; i < newOrder.length; i++) {
			System.out.print(LegacySession.colors[newOrder[i]]);
			for (int j = 0; j < newOrder.length; j++) {
				if (i != j) {
					System.out.print("\t");
					// incorrect
					// System.out.print(100 * (1.0 - (stimulusCorrect[i][j] /
					// stimulusCounts[i][j])));

					// flipped
					// System.out.print(100 * (1.0 - (stimulusCorrect[j][i] /
					// stimulusCounts[j][i])));

					// correct
					System.out.print(100 * (stimulusCorrect[newOrder[i]][newOrder[j]] / stimulusCounts[newOrder[i]][newOrder[j]]));

					// counts
					// System.out.print(stimulusCounts[i][j]);
				}
				else {
					System.out.print("\t*");
				}
			}
			System.out.println();
		}

	}

	@Override
	public String[] getLabels()
	{
		return null;
	}

}

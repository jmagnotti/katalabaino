package legacy;

import java.util.HashMap;
import java.util.Vector;

public class LegacySession implements Comparable<LegacySession>
{
	public static final int					CHANGE_NO_CHANGE		= 1;
	public static final int					FORCED_CHOICE			= 2;

	public static final int					SAME_DIFFERENT			= 3;
	public static final int					NC_FORCED_CHOICE		= 4;
	public static final int					CHOOSE_SAME_RANDOM		= 8;

	public static final int					RET_STROOP_CNC			= 6;
	public static final int					ENC_STROOP_CNC			= 7;

	public static final int					CHOOSE_SAME_LINEUP		= 8;

	public static final int					GROUP_A					= 1;
	public static final int					GROUP_B					= 2;

	public static final int					WARM_UP_SESSION_SIZE	= 20;

	public static final String[]			colors					= { "aqua", "blue", "gray",
			"green", "lime", "navy", "orange", "pink", "red", "white", "yellow", "yellowGreen",
			"brown", "purple"										};

	private static HashMap<String, Integer>	colorToInt				= null;

	private static Vector<Integer>			positions				= null;

	public String							id, label, fileName;
	public int								reportingMethod;
	public int								group;

	private String[]						labels;

	private Vector<Trial>					trials;

	public LegacySession()
	{
		trials = new Vector<Trial>();
		labels = new String[12];
	}

	private static void buildColorMap()
	{
		colorToInt = new HashMap<String, Integer>();

		for (int i = 0; i < colors.length; i++)
			colorToInt.put(colors[i], i);
	}

	public Vector<Trial> getTrials()
	{
		return trials;
	}

	public void setTrials(Vector<Trial> trials)
	{
		this.trials = trials;
	}

	public boolean isWarmUpSession()
	{
		return trials.size() <= WARM_UP_SESSION_SIZE;
	}

	/**
	 * does the assignment in the method, because we don't know the reportingMethod of the session
	 * at construction time.
	 */
	String[] getLabels()
	{
		if (reportingMethod == LegacySession.FORCED_CHOICE) {
			labels = new String[9];
			labels[0] = "4:4";
			labels[1] = "4:3";
			labels[2] = "4:2";
			labels[3] = "6:6";
			labels[4] = "6:3";
			labels[5] = "6:2";
			labels[6] = "8:8";
			labels[7] = "8:4";
			labels[8] = "8:2";
			/*
			 * labels[0] = "4:4"; labels[1] = "4:3"; labels[2] = "4:2"; labels[3] = "5:5"; labels[4]
			 * = "5:4"; labels[5] = "5:3"; labels[6] = "5:2"; labels[7] = "6:6"; labels[8] = "6:5";
			 * labels[9] = "6:4"; labels[10] = "6:3"; labels[11] = "6:2";
			 */

		}
		else if (reportingMethod == LegacySession.CHANGE_NO_CHANGE) {
			labels = new String[9];
			labels[0] = "4:4";
			labels[1] = "4:2";
			labels[2] = "4:1";
			labels[3] = "6:6";
			labels[4] = "6:3";
			labels[5] = "6:1";
			labels[6] = "8:8";
			labels[7] = "8:4";
			labels[8] = "8:1";
		}
		else if (reportingMethod == LegacySession.SAME_DIFFERENT) {
			labels[0] = "4:4";
			labels[1] = "4:2";
			labels[2] = "4:1";
			labels[3] = "5:5";
			labels[4] = "5:3";
			labels[5] = "5:1";
			labels[6] = "6:6";
			labels[7] = "6:3";
			labels[8] = "6:1";
		}
		else if (reportingMethod == LegacySession.NC_FORCED_CHOICE) {
			// labels[0] = "4:4";
			// labels[1] = "4:3";
			// labels[2] = "4:2";
			// labels[3] = "5:5";
			// labels[4] = "5:3";
			// labels[5] = "5:2";
			// labels[6] = "6:6";
			// labels[7] = "6:3";
			// labels[8] = "6:2";

			// labels[0] = "4:4";
			// labels[1] = "4:3";
			// labels[2] = "4:2";
			// labels[3] = "5:5";
			// labels[4] = "5:4";
			// labels[5] = "5:3";
			// labels[6] = "5:2";
			// labels[7] = "6:6";
			// labels[8] = "6:5";
			// labels[9] = "6:4";
			// labels[10] = "6:3";
			// labels[11] = "6:2";

			labels[0] = "2:2";
			labels[1] = "2:4";
			labels[2] = "2:6";
			labels[3] = "2:8";
			labels[4] = "4:2";
			labels[5] = "4:4";
			labels[6] = "4:6";
			labels[7] = "4:8";
			labels[8] = "6:2";
			labels[9] = "6:4";
			labels[10] = "6:6";
			labels[11] = "6:8";
		}
		else if (reportingMethod == LegacySession.RET_STROOP_CNC) {
			labels = new String[6];
			labels[0] = "4:4";
			labels[1] = "4:2";
			labels[2] = "4:1";
			labels[3] = "6:6";
			labels[4] = "6:3";
			labels[5] = "6:1";
		}
		else if (reportingMethod == LegacySession.CHOOSE_SAME_LINEUP) {
			labels = new String[16];
			labels[0] = "1:2";
			labels[1] = "1:4";
			labels[2] = "1:6";
			labels[3] = "1:8";

			labels[4] = "2:2";
			labels[5] = "2:4";
			labels[6] = "2:6";
			labels[7] = "2:8";

			labels[8] = "4:2";
			labels[9] = "4:4";
			labels[10] = "4:6";
			labels[11] = "4:8";

			labels[12] = "6:2";
			labels[13] = "6:4";
			labels[14] = "6:6";
			labels[15] = "6:8";
		}
		return labels;
	}

	public String getFullID()
	{
		return fileName.substring(0, fileName.length() - 4);
	}

	public static int ColorToInt(String colorName)
	{
		if (colorToInt == null) buildColorMap();

		String color = colorName;

		// check for Stroop stimuli
		if (colorName.contains("-")) {
			color = colorName.split("-")[1];
		}

		return colorToInt.get(color);
	}

	public static Vector<Integer> GetLineUpPositions(int nStimuli)
	{
		positions = new Vector<Integer>(nStimuli);

		int start = 29 - (nStimuli / 2);

		// if (nStimuli == 2) {
		// start = 28;
		// }
		// else if (nStimuli == 4) {
		// start = 27;
		// }
		// else if (nStimuli == 6) {
		// start = 26;
		// }
		// else if (nStimuli == 8) {
		// start = 25;
		// }

		for (int i = start; i < start + nStimuli; i++)
			positions.add(i);

		return positions;
	}

	@Override
	public int compareTo(LegacySession other)
	{
		return fileName.compareTo(other.fileName);
	}
}

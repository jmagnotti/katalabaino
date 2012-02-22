package core;

public class PositionHelper
{

	public static final int	XPOS	= 0;
	public static final int	YPOS	= 1;

	/**
	 * Returns the center coordinate for the given position. Note that these are in SCREEN
	 * coordinates. YMMV if something silly is done, like inverting the monitor.
	 * 
	 * @param position
	 *            The index to convert, in the range {1,2,...,16}
	 * @param whichCoordinate
	 *            The dimension to retreive, one of {XPOS, YPOS}
	 * @return The X or Y coordinate of the given position.
	 */
	public static double PigeonFC_CDLookUp(int position, int whichCoordinate)
	{
		if (XPOS == whichCoordinate) {
			if (position % 4 == 1) return 252 + 25;
			if (position % 4 == 2) return 334 + 25;
			if (position % 4 == 3) return 416 + 25;
			if (position % 4 == 0) return 498 + 25;
		}
		// YPOS == whichCoordinate
		if (position <= 4) return 125 + 25;
		if (position <= 8) return 182 + 25;
		if (position <= 12) return 239 + 25;
		// if (position > 12) return 296 + 25;

		return 296 + 25;
	}

	// public static void main(String[] args)
	// {
	// for (int q = 0; q <= 1; q++) {
	// System.out.println();
	// for (int i = 1; i <= 16; i++)
	// System.out.print(PigeonFC_CDLookUp(i, q) + ",");
	// }
	// }
}

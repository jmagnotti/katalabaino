package core.trial;

import java.util.HashMap;

public class Colors {
	private static Colors colors = null;

	public static final int NONE = -1;

	public static final int AQUA = 0;
	public static final int BROWN = 1;
	public static final int BLUE = 2;
	public static final int GRAY = 3;
	public static final int GREEN = 4;
	public static final int LIME = 5;
	public static final int NAVY = 6;
	public static final int ORANGE = 7;
	// Note that we are purposely conflating PINK and MAGENTA
	public static final int PINK = 8;
	public static final int MAGENTA = 8;
	// --
	public static final int PURPLE = 9;
	public static final int RED = 10;
	public static final int WHITE = 11;
	public static final int YELLOW = 12;
	public static final int YELLOWGREEN = 13;

	public static final int CLIP_ART = 14;
	public static final int KSCOPE = 15;
	public static final int TRAVEL_SLIDE = 16;
	public static final int COUNTING_STROOP = 17;
	public static final int SNODGRASS = 18;
	public static final int KANJI = 19;

	public static final int OBJECT_STROOP = 20;
	public static final int SHADED_CUBE = 21;
	public static final int POLYGON = 22;
	public static final int SNAKE = 23;
	public static final int SPIDER = 24;

	public static final int BLACK = 25;

	public static final int COLORS[] = { AQUA, BLUE, GRAY, GREEN, LIME, NAVY, ORANGE, PINK, RED, WHITE, YELLOW,
			YELLOWGREEN, PURPLE, BROWN, CLIP_ART, KSCOPE, TRAVEL_SLIDE, COUNTING_STROOP, SNODGRASS, KANJI,
			OBJECT_STROOP, SHADED_CUBE, POLYGON, SNAKE, SPIDER, BLACK };

	public HashMap<Integer, String> colorIDToLabel;
	public HashMap<String, Integer> abbrToID;

	private Colors() {
		// put hashtables in here with all different kinds of maps
		abbrToID = new HashMap<String, Integer>();
		abbrToID.put("mag", MAGENTA);
		abbrToID.put("pin", PINK);

		abbrToID.put("whi", WHITE);
		abbrToID.put("pur", PURPLE);
		abbrToID.put("ora", ORANGE);

		abbrToID.put("yel", YELLOW);
		abbrToID.put("ygr", YELLOWGREEN);

		abbrToID.put("blu", BLUE);
		abbrToID.put("gra", GRAY);

		// for Arash's CD task w/ gray background
		abbrToID.put("bla", BLACK);

		// this is for Pigeon FC_CD which was originally created with a "lime"
		// color called Green
		// (because it is RGB [0,255,0])
		// the other Green (green.jpg) used by Human CD is special-cased in the
		// fileToColorID
		// function
		abbrToID.put("gre", LIME);
		abbrToID.put("dgr", GREEN);

		abbrToID.put("lim", LIME);
		abbrToID.put("aqu", AQUA);
		abbrToID.put("red", RED);
		abbrToID.put("bro", BROWN);
		abbrToID.put("nav", NAVY);

		// polygon stimuli are all white
		abbrToID.put("pol", WHITE);

		// line tilt stimuli are all gray
		abbrToID.put("lin", GRAY);

		// what color are shaded cubes?
		abbrToID.put("cub", SHADED_CUBE);

		colorIDToLabel = new HashMap<Integer, String>();
		colorIDToLabel.put(AQUA, "Aqua");
		colorIDToLabel.put(BLUE, "Blue");
		colorIDToLabel.put(GRAY, "Gray");
		colorIDToLabel.put(GREEN, "Green");
		colorIDToLabel.put(LIME, "Lime");
		colorIDToLabel.put(NAVY, "Navy");
		colorIDToLabel.put(ORANGE, "Orange");
		colorIDToLabel.put(PINK, "Pink/Magenta");
		colorIDToLabel.put(RED, "Red");
		colorIDToLabel.put(WHITE, "White");
		colorIDToLabel.put(YELLOW, "Yellow");
		colorIDToLabel.put(YELLOWGREEN, "YellowGreen");
		colorIDToLabel.put(PURPLE, "Purple");
		colorIDToLabel.put(BROWN, "Brown");
		colorIDToLabel.put(BLACK, "Black");

		colorIDToLabel.put(CLIP_ART, "ClipArt");

		colorIDToLabel.put(KSCOPE, "Kaleidoscope");
		colorIDToLabel.put(KANJI, "Kanji");
		colorIDToLabel.put(SNODGRASS, "Snodgrass");

		colorIDToLabel.put(OBJECT_STROOP, "Object Stroop");

		colorIDToLabel.put(SHADED_CUBE, "Shaded Cube");

		colorIDToLabel.put(POLYGON, "Polygon");

		colorIDToLabel.put(SPIDER, "Spider");
		colorIDToLabel.put(SNAKE, "Snake");
	}

	public static Colors GetInstance() {
		if (colors == null)
			colors = new Colors();

		return colors;
	}

	public int fileToColorID(String string) {
		// we have to special case some of the strings
		if (string.equalsIgnoreCase("yellowGreen.jpg"))
			return YELLOWGREEN;
		if (string.equalsIgnoreCase("green.jpg"))
			return GREEN;

		if (!abbrToID.containsKey(string.substring(0, 3))) {
			return GRAY;
		}

		return abbrToID.get(string.substring(0, 3).toLowerCase());
	}

	/**
	 * we're taking advantage of the ordering of the constants for this method,
	 * double check the constants list so you know what is considered "basic"
	 * (e.g., Polygons are NOT basic)
	 * 
	 * @param cid
	 * @return
	 */
	public boolean isBasicColor(int cid) {
		return (cid == BLACK || cid <= YELLOWGREEN);
	}

}

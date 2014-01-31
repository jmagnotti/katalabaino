package core;

import java.util.HashMap;

public class Shapes
{

	public static final int			NONE			= -1;

	public static final int			CIRCLE			= 0;
	public static final int			BUTTERFLY		= 1;
	public static final int			HEART			= 2;
	public static final int			CLUBS			= 3;
	public static final int			PENTAGON		= 4;
	public static final int			RECTANGLE		= 5;
	public static final int			SPIRAL			= 6;
	public static final int			TRIANGLE		= 7;

	public static final int			POLYGON			= 8;
	public static final int			RECT_IMAGE		= 9;
	public static final int			KSCOPE			= 10;
	public static final int			TRAVEL_SLIDE	= 11;
	public static final int			COUTING_STROOP	= 12;
	public static final int			KANJI			= 13;
	public static final int			CLIP_ART		= 14;
	public static final int			SNODGRASS		= 15;
	public static final int			LINE_SEGMENT	= 16;
	public static final int			SHADED_CUBE		= 17;


	public static final int			SHAPES[]		= { CIRCLE, BUTTERFLY, HEART, CLUBS, PENTAGON,
			RECTANGLE, SPIRAL, TRIANGLE, POLYGON, RECT_IMAGE, KSCOPE, TRAVEL_SLIDE, COUTING_STROOP,
			KANJI, CLIP_ART, SNODGRASS, LINE_SEGMENT, SHADED_CUBE };

	public HashMap<Integer, String>	shapeIDToLabel;
	public HashMap<String, Integer>	abbrToID;

	public static Shapes GetInstance()
	{
		return new Shapes();
	}

	private Shapes()
	{
		abbrToID = new HashMap<String, Integer>();
		shapeIDToLabel = new HashMap<Integer, String>();

		// put hashtables in here with all different kinds of maps
		abbrToID.put("c", CIRCLE);
		abbrToID.put("b", BUTTERFLY);
		abbrToID.put("h", HEART);
		abbrToID.put("l", CLUBS);
		abbrToID.put("poly", POLYGON);
		abbrToID.put("p", PENTAGON);
		abbrToID.put("r", RECTANGLE);
		abbrToID.put("s", SPIRAL);
		abbrToID.put("t", TRIANGLE);
		abbrToID.put("k", KSCOPE);
		abbrToID.put("T", KSCOPE);

		shapeIDToLabel.put(CIRCLE, "Circle");
		shapeIDToLabel.put(BUTTERFLY, "Buttferly");
		shapeIDToLabel.put(HEART, "Heart");
		shapeIDToLabel.put(CLUBS, "Clubs");
		shapeIDToLabel.put(PENTAGON, "Pentagon");
		shapeIDToLabel.put(RECTANGLE, "Rectangle");
		shapeIDToLabel.put(SPIRAL, "Spiral");
		shapeIDToLabel.put(TRIANGLE, "Triangle");

		shapeIDToLabel.put(POLYGON, "Polygon");

		shapeIDToLabel.put(RECT_IMAGE, "Image");

		shapeIDToLabel.put(KSCOPE, "Kaleidoscope");

		shapeIDToLabel.put(TRAVEL_SLIDE, "TravelSlide");

		shapeIDToLabel.put(COUTING_STROOP, "CountingStroop");

		shapeIDToLabel.put(KANJI, "Kanji");
		shapeIDToLabel.put(CLIP_ART, "Clip Art");
		
		shapeIDToLabel.put(SHADED_CUBE, "Shaded Cube");
		
	}
}

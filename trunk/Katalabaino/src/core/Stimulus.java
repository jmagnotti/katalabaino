package core;

import java.util.HashMap;

public class Stimulus {
	public String file, label;
	public int colorID, shapeID, position;

	public Stimulus() {
	}

	public Stimulus(String fileName, int pos) {
		file = fileName;

		// special-case the polygon stimuli
		if (fileName.startsWith("p") && !fileName.startsWith("purp")) {
			colorID = Colors.WHITE;
			shapeID = Shapes.POLYGON;
		} else if (fileName.startsWith("k")) {
			colorID = Colors.KSCOPE;
			shapeID = Shapes.KSCOPE;
		} else if (fileName.startsWith("line")) {
			colorID = Colors.GRAY;
			shapeID = Shapes.LINE_SEGMENT;
		} else if (fileName.startsWith("cube")) {
			colorID = Colors.SHADED_CUBE;
			shapeID = Shapes.SHADED_CUBE;
		} else if (fileName.startsWith("col")) {
			switch (Integer.parseInt(fileName.substring(3, 4))) {
			case 1:
				colorID = Colors.RED;
				break;
			case 2:
				colorID = Colors.LIME;
				break;
			case 3:
				colorID = Colors.BLUE;
				break;
			case 4:
				colorID = Colors.YELLOW;
				break;
			case 5:
				colorID = Colors.AQUA;
				break;
			case 6:
				colorID = Colors.MAGENTA;
				break;
			}

			shapeID = Shapes.RECTANGLE;
		} else {
			colorID = Colors.GetInstance().fileToColorID(fileName);

			// we need to figure out how to extrat the Shape ID in the most
			// general fashion
			int start = fileName.indexOf(".") - 1;

			String shapeKey = fileName.substring(start, start + 1);

			HashMap<String, Integer> shapeMap = Shapes.GetInstance().abbrToID;
			if (shapeMap.containsKey(shapeKey))
				shapeID = shapeMap.get(shapeKey);
			else
				shapeID = Shapes.NONE;
		}

		position = pos;

		label = Colors.GetInstance().colorIDToLabel.get(colorID) + ":"
				+ Shapes.GetInstance().shapeIDToLabel.get(shapeID);
	}

	public Stimulus(String filename, int pos, int cid, int sid, String label) {
		file = filename;
		position = pos;
		colorID = cid;
		shapeID = sid;
		this.label = label;
	}

}

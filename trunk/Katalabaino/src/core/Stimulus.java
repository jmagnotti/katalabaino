package core;

public class Stimulus
{
	public String	file, label;
	public int		colorID, shapeID, position;

	public Stimulus()
	{}

	public Stimulus(String fileName, int pos)
	{
		file = fileName;

		// special-case the polygon stimuli
		if (fileName.startsWith("p") && !fileName.startsWith("purp")) {
			colorID = Colors.WHITE;
			shapeID = Shapes.POLYGON;
		}
		else if (fileName.startsWith("k")) {
			colorID = Colors.KSCOPE;
			shapeID = Shapes.KSCOPE;
		}
		else {
			colorID = Colors.GetInstance().fileToColorID(fileName);

			// we need to figure out how to extrat the Shape ID in the most general fashion
			int start = fileName.indexOf(".") - 1;

			shapeID = Shapes.GetInstance().abbrToID.get(fileName.substring(start, start + 1));
		}

		position = pos;

		label = Colors.GetInstance().colorIDToLabel.get(colorID) + ":"
				+ Shapes.GetInstance().shapeIDToLabel.get(shapeID);
	}

	public Stimulus(String filename, int pos, int cid, int sid, String label)
	{
		file = filename;
		position = pos;
		colorID = cid;
		shapeID = sid;
		this.label = label;
	}

}

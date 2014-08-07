package core.analysis;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import core.StringMatrix;
import core.session.Session;

public class CombinedAnalysis extends Analysis {

	private String outputFile;
	private Vector<File> tempFiles;

	public CombinedAnalysis(Vector<? extends Session> data, String outputFile) {
		super(data);
		this.outputFile = outputFile;
		tempFiles = new Vector<File>();
		field_delimiter = ",";
	}

	@Override
	public void analyze() {
		try {
			tempFiles.add(File.createTempFile("kata", null));
			tempFiles.lastElement().deleteOnExit();
			super.setOutputLocation(tempFiles.lastElement());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		super.analyze();
	}

	/**
	 * convenience function to clear the maps/filters/splitters after analysis
	 * 
	 * @param clear
	 */
	public void analyze(boolean clear) {
		this.analyze();
		if (clear)
			this.clearAll();
	}

	public void accumulate() throws IOException {
		accumulate(false);
	}

	public void setOutputFile(String pathToFile) {
		outputFile = pathToFile;
	}

	public void accumulate(boolean addSpace) throws IOException {
		StringMatrix sm = StringMatrix.Build(tempFiles.firstElement());

		for (int i = 1; i < tempFiles.size(); i++) {
			if (addSpace)
				sm.join(new StringMatrix(1, 1));

			sm.join(StringMatrix.Build(tempFiles.get(i)));
		}
		sm.toFile(new File(outputFile));
	}
}

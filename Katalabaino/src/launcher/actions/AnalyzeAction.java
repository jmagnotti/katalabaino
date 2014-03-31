package launcher.actions;

import java.io.File;
import java.util.Vector;

import launcher.WatchAction;

public abstract class AnalyzeAction implements WatchAction {

	protected Vector<File> watchFiles;
	protected String analysisDir;

	protected AnalyzeAction(String dir, String[] birdNames) {
		analysisDir = dir;
		if (!analysisDir.endsWith("/"))
			analysisDir = analysisDir + "/";

		watchFiles = new Vector<File>();
		for (String bird : birdNames) {
			watchFiles.add(new File(analysisDir	+ bird));
		}
	}

	@Override
	public Vector<File> getWatchFiles() {
		return watchFiles;
	}

}

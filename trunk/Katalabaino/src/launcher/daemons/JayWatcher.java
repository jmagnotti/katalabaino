package launcher.daemons;

import core.constants.BirdNames;
import launcher.FileWatcher;
import launcher.actions.SDAcquisitionAnalyzeAction;

public class JayWatcher {

	public static void main(String[] args) {
		FileWatcher fw = new FileWatcher(new SDAcquisitionAnalyzeAction("Z:/warehouse/jay/", BirdNames.JAYS));
		fw.run();
	}
}

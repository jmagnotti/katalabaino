package launcher.daemons;

import launcher.FileWatcher;
import launcher.actions.SDAcquisitionAnalyzeAction;
import core.constants.BirdNames;

public class MagpieWatcher {

	public static void main(String[] args) {
		FileWatcher fw = new FileWatcher(new SDAcquisitionAnalyzeAction("Z:/warehouse/magpie", BirdNames.MAGPIES));
//		 FileWatcher fw = new FileWatcher(new
//		 SDTransferAnalyzeAction("Z:/warehouse/magpie", BirdNames.MAGPIES));

		fw.run();
	}
}

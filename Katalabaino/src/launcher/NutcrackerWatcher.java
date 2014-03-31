package launcher;

import launcher.actions.SDTransferAnalyzeAction;

public class NutcrackerWatcher extends FileWatcher {

	public NutcrackerWatcher() {
		super();
	}

	public static void main(String[] args) {
		NutcrackerWatcher mw = new NutcrackerWatcher();
//		mw.addWatchAction(new SDAcquisitionAnalyzeAction("Z:/warehouse/nutcracker", BirdNames.NUTCRACKERS));
		mw.addWatchAction(new SDTransferAnalyzeAction("Z:/warehouse/nutcracker", BirdNames.NUTCRACKERS));
		mw.run();
	}
}

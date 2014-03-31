package launcher;

import launcher.actions.MagpieTransferAnalyzeAction;

public class MagpieWatcher extends FileWatcher {

	public MagpieWatcher() {
	}

	public static void main(String[] args) {
		MagpieWatcher mw = new MagpieWatcher();
		// mw.addWatchAction(new MagpieAcquisitionAnalyzeAction());
		mw.addWatchAction(new MagpieTransferAnalyzeAction());
		mw.run();
	}
}

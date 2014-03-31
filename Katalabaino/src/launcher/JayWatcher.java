package launcher;

import launcher.actions.JayAcquisitionAnalyzeAction;

public class JayWatcher extends FileWatcher {

	public JayWatcher() {
		super();
	}

	public static void main(String[] args) {
		JayWatcher mw = new JayWatcher();
		mw.addWatchAction(new JayAcquisitionAnalyzeAction());
		mw.run();
	}
}

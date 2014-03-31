package launcher;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;

public class FileWatcher implements Runnable {
	private Vector<WatchAction> watchActions;
	private HashMap<File, Long> modificationDates;

	public static int WAIT_TIME = 3 * 1000;

	private static boolean keepGoing;

	protected FileWatcher() {
		keepGoing = true;
		watchActions = new Vector<WatchAction>();
		modificationDates = new HashMap<File, Long>();
	}

	public FileWatcher(WatchAction wa) {
		keepGoing = true;
		watchActions = new Vector<WatchAction>();
		modificationDates = new HashMap<File, Long>();

		addWatchAction(wa);
	}

	public void addWatchAction(WatchAction wa) {
		watchActions.add(wa);

		for (File f : wa.getWatchFiles()) {
			modificationDates.put(f, f.lastModified());

			System.out.println(f.getName() + ": " + f.lastModified());
		}
	}

	public void removeWatchAction(WatchAction wa) {
		watchActions.remove(wa);
	}

	@Override
	public void run() {
		while (keepGoing()) {
			try {
				checkAndUpdate();
				synchronized (this) {
					this.wait(WAIT_TIME);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				setKeepGoing(false);
			}
		}
	}

	public boolean keepGoing() {
		return keepGoing;
	}

	synchronized public void setKeepGoing(boolean flag) {
		keepGoing = flag;
	}

	private void checkAndUpdate() {

		for (WatchAction wa : watchActions) {
			Vector<File> changed = new Vector<File>();
			for (File file : wa.getWatchFiles()) {
				if (file.lastModified() > modificationDates.get(file)) {
					changed.add(file);
				}
			}
			if (changed.size() > 0) {
				wa.handleFileChangedEvent(changed);
				// putting this here so that any changes that occur during the
				// call to handle are duplicated
				for (File f : changed) {
					modificationDates.put(f, f.lastModified() + 1L);
				}
			}
		}
	}
}
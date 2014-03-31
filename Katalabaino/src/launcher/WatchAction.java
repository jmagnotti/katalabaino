package launcher;

import java.io.File;
import java.util.Vector;

public interface WatchAction {

	public Vector<File> getWatchFiles();

	public void handleFileChangedEvent(Vector<File> changes);
}

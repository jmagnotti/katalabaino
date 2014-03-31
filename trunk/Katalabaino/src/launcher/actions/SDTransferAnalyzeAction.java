package launcher.actions;

import java.io.File;
import java.util.Vector;

import analyses.NH_SD_Transfer;

public class SDTransferAnalyzeAction extends AnalyzeAction {

	public SDTransferAnalyzeAction(String dir, String[] names) {
		super(dir, names);
	}

	@Override
	public void handleFileChangedEvent(Vector<File> changes) {
		for (File changed : changes) {
			new NH_SD_Transfer(analysisDir, changed.getName()).run();
		}
	}
}

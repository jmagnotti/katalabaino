package launcher.actions;

import java.io.File;
import java.util.Vector;

import analyses.NH_SD_Acquisition;

public class SDAcquisitionAnalyzeAction extends AnalyzeAction {

	public SDAcquisitionAnalyzeAction(String dir, String [] names) {
		super(dir, names);
	}
	
	@Override
	public void handleFileChangedEvent(Vector<File> changes) {
		for (File changed : changes) {
			new NH_SD_Acquisition(analysisDir, changed.getName()).run();
		}

	}

}

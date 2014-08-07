package launcher.daemons;

import java.io.File;

import file.MDBTRFilter;

public class NutcrackerWatcher {

	public static void main(String[] args) {
		// FileWatcher mw = new FileWatcher(new
		// SDAcquisitionAnalyzeAction("Z:/warehouse/nutcracker",
		// BirdNames.NUTCRACKERS));
		// FileWatcher mw = new FileWatcher(new
		// SDTransferAnalyzeAction("Z:/warehouse/nutcracker",
		// BirdNames.NUTCRACKERS));
		// mw.run();

		for (File f : new File("Z:/warehouse/nutcracker/lance/").listFiles(new MDBTRFilter())) {
			System.out.println(f.getName());
		}

	}
}

package launcher.actions;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.Vector;

import launcher.BirdNames;
import launcher.WatchAction;

public class MagpieCopyAction implements WatchAction {

	public static String magpieWarehouse = "Z:/warehouse/magpie/";

	private Vector<File> filesToWatch;

	public MagpieCopyAction() {
		filesToWatch = new Vector<File>();
		filesToWatch.add(new File("Z:/Dropbox/AU_Projects/deb/Birdbrain11/"));
	}

	@Override
	public Vector<File> getWatchFiles() {
		return filesToWatch;
	}

	@Override
	public void handleFileChangedEvent(Vector<File> changes) {

		// we are getting the entire directory, so scan for files we actually
		// care about
		Vector<File> filesToCopy = new Vector<File>();

		for (File changedFile : changes) {
			if (changedFile.isDirectory()) {
				File[] files = changedFile.listFiles(new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						return pathname.getName().endsWith(".tr");
					}
				});
				for (File file : files)
					filesToCopy.add(file);
			}
		}

		Vector<File> destinations = new Vector<File>(filesToCopy.size());
		for (File f : filesToCopy) {
			try {

				String birdName = BirdNames.GetNameFromFileName(f.getName(), BirdNames.MAGPIES);

				if (!birdName.equalsIgnoreCase("UNKNOWN")) {
					destinations.add(new File(magpieWarehouse + birdName + "/" + f.getName()));
					Files.copy(f.toPath(), destinations.lastElement().toPath(), new CopyOption[] { REPLACE_EXISTING });
				}
			} catch (IOException e) {
				System.out.println("Couldn't copy file: " + f.getName() + ". Moving along...");
				// e.printStackTrace();
			}
		}
	}
}

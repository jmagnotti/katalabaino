package setup;

import java.io.File;
import java.io.FileFilter;

public class PushResultFileNumbers {

	public static void main(String[] args) {
		String location = "Z:/Dropbox/AU_Projects/katz/target_search/For John/n-item_mts/Shape_Train/Mars_3900_series/";
		int offset = 500;

		String birdName = "mars";
		File[] filesToChange = new File(location).listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".mdb") || pathname.getName().endsWith(".tr");
			}
		});

		for (File f : filesToChange) {
			String[] tokens = f.getName().split("\\.");
			int originalSession = Integer.parseInt(tokens[0].split(birdName)[1]);
			// System.out.println(originalSession);

			int newSession = offset + originalSession;
			File newFile = new File(f.getParent() + "/" + birdName + newSession + "." + tokens[1]);
			f.renameTo(newFile);
		}

	}
}

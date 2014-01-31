package analyses;

import java.io.File;
import java.io.FileFilter;

public class ConvertMDBTOTS {
	public static void main(String[] args) {
		convertFile(new File("Z:/warehouse/session_mod/half_sessions/"));
	}

	public static void convertFile(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					return pathname.getName().endsWith("mdb") | pathname.isDirectory();
				}
			});
			for (File ff : files) {
				convertFile(ff);
			}
		} else {
			System.out.println("Renaming: " + f.getAbsolutePath());

			f.renameTo(new File(f.getPath().substring(0, f.getPath().length() - 3) + "ts"));

		}
	}

}

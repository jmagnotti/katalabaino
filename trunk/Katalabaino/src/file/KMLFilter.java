package file;

import java.io.File;
import java.io.FilenameFilter;

public class KMLFilter implements FilenameFilter {
	public KMLFilter() {
	}

	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith("kml");
	}
}

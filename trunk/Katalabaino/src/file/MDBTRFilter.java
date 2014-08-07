package file;

import java.io.File;
import java.io.FilenameFilter;

public class MDBTRFilter implements FilenameFilter
{
	public MDBTRFilter()
	{}

	@Override
	public boolean accept(File dir, String name)
	{
		return name.endsWith("mdb") || name.endsWith("tr");
	}
}

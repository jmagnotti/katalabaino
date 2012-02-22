package core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileTypeConverter
{
	private static final int	BUFFER	= 2048;

	public static Vector<File> ConvertToKML(Vector<? extends Session> sessions) throws Exception
	{
		System.out.println("Building " + sessions.size() + " files...");

		Vector<File> kmlFiles = new Vector<File>();

		System.out.println("Printing...");
		for (Session session : sessions) {

			kmlFiles.add(new File(
					session.resultsFile.substring(0, session.resultsFile.length() - 3) + "kml"));

			session.toXML(kmlFiles.lastElement());
		}

		System.out.println("Done.");

		return kmlFiles;
	}

	public static File ZipToDBO(String name, Vector<File> files) throws Exception
	{
		return ZipToDBO(name, files, true);
	}

	public static File ZipToDBO(String name, Vector<File> files, boolean cleanUp) throws Exception
	{

		if (!name.endsWith("dbo")) name = name + ".dbo";

		File zipFile = new File(name);

		BufferedInputStream origin = null;
		FileOutputStream dest = new FileOutputStream(zipFile);
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

		byte data[] = new byte[BUFFER];

		for (int i = 0; i < files.size(); i++) {
			System.out.println("Adding: " + files.get(i));

			FileInputStream fi = new FileInputStream(files.get(i));

			origin = new BufferedInputStream(fi, BUFFER);

			ZipEntry entry = new ZipEntry(files.get(i).getName());
			out.putNextEntry(entry);

			int count;
			while ((count = origin.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			origin.close();
		}
		out.close();

		if (cleanUp) for (File f : files)
			f.deleteOnExit();

		return zipFile;
	}

	public static void CreateZipFileFromDirectory(String workDir, String bird, Session prototype)
			throws Exception
	{
		File files[] = new File(workDir).listFiles(new MDBTRFilter());

		System.out.println(workDir);

		Vector<Session> sessions = SessionFactory.BuildSessions(prototype, files);

		FileTypeConverter.ZipToDBO(workDir + bird, FileTypeConverter.ConvertToKML(sessions));
	}

}

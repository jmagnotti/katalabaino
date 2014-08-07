package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import core.session.Session;
import core.session.SessionFactory;

public class FileTypeConverter {
	private static final int BUFFER = 2048;

	public static Vector<File> ConvertToKML(Vector<? extends Session> sessions) throws IOException {
		System.out.println("Building " + sessions.size() + " files...");

		Vector<File> kmlFiles = new Vector<File>();

		System.out.println("Printing...");
		for (Session session : sessions) {

			kmlFiles.add(new File(session.resultsFile.substring(0, session.resultsFile.length() - 3) + "kml"));

			session.toXML(kmlFiles.lastElement());
		}

		System.out.println("Done.");

		return kmlFiles;
	}

	public static File ZipToDBO(String name, Vector<File> files) throws IOException {
		return ZipToDBO(name, files, true);
	}

	public static File ZipToDBO(String name, Vector<File> files, boolean cleanUp) throws IOException {

		if (!name.endsWith("dbo"))
			name = name + ".dbo";

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

		if (cleanUp) {
			for (File f : files)
				f.deleteOnExit();
		}
		return zipFile;
	}

	public static File CreateZipFileFromDirectory(String workDir, String fileName, Session prototype)
			throws IOException, SQLException, SAXException, ParserConfigurationException {

		File files[] = new File(workDir).listFiles(new MDBTRFilter());

		System.out.println(workDir);

		// check to see if there is already a DBO here with modification
		// date < the date of the newest file
		boolean needRebuild = true;

		File zipFile = null;
		if (files.length < 1) {
			// check if there is a dbo with the birds name on it in the current
			// directory
			File f = new File(workDir + fileName + ".dbo");
			if (f.exists())
				zipFile = f;

			needRebuild = false;
		} else {

			if (!fileName.endsWith("dbo"))
				fileName = fileName + ".dbo";

			zipFile = new File(workDir + fileName);
			if (zipFile.exists()) {
				long lm = zipFile.lastModified();
				long mxlm = -1;

				for (File file : files) {
					if (file.lastModified() > mxlm)
						mxlm = file.lastModified();
				}
				needRebuild = mxlm > lm;
			}

			if (needRebuild) {
				Vector<Session> sessions = SessionFactory.BuildSessions(prototype, files);
				zipFile = FileTypeConverter.ZipToDBO(workDir + fileName, FileTypeConverter.ConvertToKML(sessions));
			}
		}
		return zipFile;
	}

}

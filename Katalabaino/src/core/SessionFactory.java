package core;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class SessionFactory {
	public static final String DB_PRFX_STR = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
	public static final String DB_SFFX_STR = ";DriverID=22;READONLY=true}";

	public static Vector<Session> BuildSessions(Session prototype, File... files) throws IOException, SQLException,
			SAXException, ParserConfigurationException {
		Vector<Session> sessions = new Vector<Session>();

		for (File f : files) {
			if (f.getName().endsWith(".dbo")) {
				Vector<Session> temp = getSessionsFromZip(f, prototype);
				for (Session sess : temp)
					sessions.add(sess);
			} else if (!f.isDirectory()) {
				sessions.add(getSessionFromFile(f, prototype));
			}
		}

		// paranoia says let's do this every time we load the data. how long can
		// it take? about 16ms with ~ 100 files
		Collections.sort(sessions);

		return sessions;
	}

	private static Vector<Session> getSessionsFromZip(File file, Session session) throws IOException, SAXException,
			ParserConfigurationException {
		Vector<Session> sessions = new Vector<Session>();
		ZipFile zipFile = new ZipFile(file);
		Enumeration<? extends ZipEntry> e = zipFile.entries();
		while (e.hasMoreElements()) {
			ZipEntry ze = e.nextElement();
			InputStream is = zipFile.getInputStream(ze);
			sessions.add(session.fromStream(is));
		}
		zipFile.close();
		return sessions;
	}

	private static Session getSessionFromFile(File file, Session session) throws IOException, SQLException,
			SAXException, ParserConfigurationException {
		if (file.getName().endsWith(".kml"))
			return session.fromXML(file);
		else if (file.getName().endsWith(".mdb"))
			return getSessionFromMDB(session, file.getAbsolutePath());
		else if (file.getName().endsWith(".tr"))
			return getSessionFromMDB(session, convertTRtoMDB(file.getAbsolutePath()));

		throw new FileNotFoundException("Invalid File Format: " + file.getAbsolutePath());
	}

	private static Session getSessionFromMDB(Session session, String filePath) throws SQLException {
		Connection sessionDB = null, resultsDB = null;
		String rfn = "", sfn = "";

		try {
			System.out.println(filePath);

			resultsDB = DriverManager.getConnection(DB_PRFX_STR + filePath + DB_SFFX_STR);

			rfn = filePath.substring(filePath.lastIndexOf("\\") + 1);

			// System.out.println(rfn);

			// we need to extract the session DB information
			Statement s = resultsDB.createStatement();
			s.execute("Select * from SessionResults");
			ResultSet rs = s.getResultSet();
			while (rs.next()) {
				sfn = new File(rs.getString("SessionFile")).getName();
			}
			rs.close();

			System.out.println(rfn);

			sfn = sfn.substring(0, sfn.length() - 2) + "mdb";

			// because of how the sessions are stored, we need to peel off the
			// subject name from the
			// hierarchy:
			// paradigm/phase/birdname/ -->
			// paradigm/phase/sessions/
			String dir = "";
			String[] t = filePath.split("\\\\");

			dir = t[0];
			for (int i = 1; i < t.length - 2; i++)
				dir = dir + "/" + t[i];
			// --

			// we need to make sure that the DB_DIR is in MDB format, not TS
			// format.
			convertTStoMDB(dir + "/sessions/");

			String sessdb = DB_PRFX_STR + dir + "/sessions/" + sfn + DB_SFFX_STR;

			System.out.println(sessdb);

			sessionDB = DriverManager.getConnection(sessdb, "", "");
		} catch (SQLException sqle) {

			if (resultsDB != null)
				resultsDB.close();

			if (sessionDB != null)
				sessionDB.close();

			throw sqle;
		}
		return session.fromMDB(sessionDB, resultsDB, sfn, rfn);
	}

	private static void convertTStoMDB(String dirName) {
		File[] files = new File(dirName).listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith("ts");
			}
		});

		if (files != null) {
			for (File f : files) {
				f.renameTo(new File(f.getPath().substring(0, f.getPath().length() - 2) + "mdb"));
			}
		}

	}

	private static String convertTRtoMDB(String filePath) throws IOException {
		File f = new File(filePath);
		String outfile = f.getPath().substring(0, f.getPath().length() - 2) + "mdb";
		if (f.renameTo(new File(outfile)))
			return outfile;

		System.err.println("Unable to rename: " + outfile);

		return outfile;
	}
}

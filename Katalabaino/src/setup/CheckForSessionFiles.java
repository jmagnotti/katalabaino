package setup;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import core.session.SessionFactory;
import file.MDBTRFilter;

public class CheckForSessionFiles {
	public static void main(String[] args) throws Exception {
		File[] results = new File("Z:/Google Drive/warehouse/pi/yogi/").listFiles(new MDBTRFilter());

		for (File file : results) {
			String sfn = "";
			Connection resultsDB = DriverManager.getConnection(SessionFactory.DB_PRFX_STR + file.getAbsolutePath()
					+ SessionFactory.DB_SFFX_STR);
			Statement s = resultsDB.createStatement();
			s.execute("Select * from SessionResults");
			ResultSet rs = s.getResultSet();
			while (rs.next()) {
				sfn = new File(rs.getString("SessionFile")).getName();
			}
			rs.close();
			resultsDB.close();
			String fname = sfn.substring(0, sfn.length() - 2) + "ts";
			// System.out.println(fname);

			if (!new File("Z:/Google Drive/warehouse/pi/sessions/" + fname).exists())
				System.err.println(sfn);
		}
	}
}

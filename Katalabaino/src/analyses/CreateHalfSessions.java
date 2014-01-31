package analyses;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import core.MDBTRFilter;
import core.SessionFactory;

public class CreateHalfSessions {
	public static void main(String[] args) throws SQLException, IOException {

		Connection sessionDB = null;
		String prefix = "Z:/warehouse/session_mod/";
		String[] filePaths = new File(prefix).list(new MDBTRFilter());

		for (String filePath : filePaths) {
			try {

				System.out.println(filePath);
				filePath = prefix + filePath.substring(0, filePath.length() - 4);
				Files.copy(new File(filePath + ".mdb").toPath(), new File(
						filePath + "A.mdb").toPath(),
						java.nio.file.StandardCopyOption.REPLACE_EXISTING);
				Files.copy(new File(filePath + ".mdb").toPath(), new File(
						filePath + "B.mdb").toPath(),
						java.nio.file.StandardCopyOption.REPLACE_EXISTING);

				sessionDB = DriverManager
						.getConnection(SessionFactory.DB_PRFX_STR + filePath
								+ "A.mdb" + SessionFactory.DB_SFFX_STR);

				// FOR "A" we remove everything 51 and above
				for (String tname : new String[] { "TrialInfo", "ProbeInfo",
						"StimuliInfo" }) {
					sessionDB.createStatement().executeUpdate(
							"DELETE * from " + tname + " WHERE trialNum > 50");
				}
				sessionDB.createStatement().executeUpdate(
						"UPDATE SessionInfo SET NumOfTrials = 50");
				sessionDB.close();

				// Now for session B
				sessionDB = DriverManager
						.getConnection(SessionFactory.DB_PRFX_STR + filePath
								+ "B.mdb" + SessionFactory.DB_SFFX_STR);
				// For "B" we remove everything 50 and below
				for (String tname : new String[] { "TrialInfo", "ProbeInfo",
						"StimuliInfo" }) {

					sessionDB.createStatement().executeUpdate(
							"DELETE * from " + tname + " WHERE trialNum < 51");

					// we need to change all the trial numbers
					for (int i = 1; i <= 50; i++) {
						sessionDB.createStatement().executeUpdate(
								"UPDATE " + tname + " SET TrialNum = " + i
										+ " WHERE TrialNum = " + (50 + i));
					}
				}

				sessionDB.createStatement().executeUpdate(
						"UPDATE SessionInfo SET NumOfTrials = 50");

			} catch (SQLException sqle) {
				if (sessionDB != null)
					sessionDB.close();

				throw sqle;
			}

			sessionDB.close();
		}
	}
}

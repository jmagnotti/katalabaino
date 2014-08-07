package sessions;

import java.sql.Connection;
import java.sql.SQLException;

import core.session.Session;

public class MovementSession extends OFSSession {

	public MovementSession() {
		super();
	}

	@Override
	public Session fromMDB(Connection session, Connection results,
			String sName, String rName) throws SQLException {

		Session mofs = super.fromMDB(session, results, sName, rName);

		mofs.comment = "MovementSession";

		return mofs;
	}
}

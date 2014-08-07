package mappers;

import java.util.Vector;

import core.Mapper;
import core.analysis.Analysis;
import core.session.Session;
import core.trial.Trial;

public class SessionInformationMap extends Mapper {
	private int includeResultsFile = -1;

	public SessionInformationMap() {

		// super(" comment\tsubject\tid\tsessionFile\tresultsFile");
		// the "space" at the beginning is a cheap way to make sure the session
		// file comes first
		super(" sessionFile");
	}

	public SessionInformationMap(int includeResultsFile) {
		super(" sessionFile" + Analysis.field_delimiter + "resultsFile");

		this.includeResultsFile = includeResultsFile;
	}

	// public SessionInformationMap(String... keys) {
	// super(implode(keys));
	// }
	//
	// // this doesn't really belong in this class...
	// private static String implode(String[] keys) {
	// String result = " " + keys[0];
	//
	// for (int i = 1; i < keys.length; i++)
	// result = "\t" + keys[i];
	//
	// return result;
	// }

	@Override
	public void nextSession(Session session) {
		resultString = new Vector<String>();

		// resultString.add(session.comment);
		// resultString.add(session.subject);
		// resultString.add(session.id);
		resultString.add(session.sessionFile);
		if (includeResultsFile > 0)
			resultString.add(session.resultsFile);
	}

	@Override
	public boolean allowSplits() {
		return false;
	}

	@Override
	public boolean needsTrials() {
		return false;
	}

	@Override
	public void nextTrial(Trial trial) {
	}

	@Override
	public Vector<String> cleanUp() {
		return resultString;
	}

}

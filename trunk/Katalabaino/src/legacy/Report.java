package legacy;

public abstract class Report {
	public String name;

	protected String[] labels;
	protected String currentID, currentGroup, currentFileName;

	public abstract void preSession(LegacySession session);

	public abstract void analyzeTrial(Trial trial);

	public abstract void postSession();

	protected Report() {
	}

	public abstract String[] getLabels();

	protected void loadSessionCharacteristics(LegacySession session) {
		currentID = session.id;
		currentGroup = "" + session.group;
		currentFileName = session.fileName;

		labels = session.getLabels();
	}

	/**
	 * Called after all sessions have been processed. default implementation is
	 * blank
	 */
	public void allFinished() {
	}
}

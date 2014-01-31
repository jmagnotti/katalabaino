package analyses;

import core.Filter;
import core.Session;
import core.Trial;

public class LookForTrialDuplicatesFilter extends Filter {

	public LookForTrialDuplicatesFilter() {
	}

	private Session currentSession;
	private int lastTrial;

	@Override
	protected boolean doAllow(Session session) {
		currentSession = session;

		return true;
	}

	@Override
	protected boolean doAllow(Trial trial) {
		if (lastTrial == trial.trialNumber) {
			System.out.println("Dupe at : " + currentSession.resultsFile
					+ ", t: " + lastTrial);
			lastTrial = trial.trialNumber;
			return false;
		}

		return true;
	}

}

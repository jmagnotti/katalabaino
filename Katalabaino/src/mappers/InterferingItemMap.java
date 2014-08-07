package mappers;

import java.util.Vector;

import core.Mapper;
import core.session.Session;
import core.trial.Trial;

public class InterferingItemMap extends Mapper {

	public InterferingItemMap() {
		super("---this is for calculation only---");
	}

	@Override
	public void nextSession(Session session) {
		System.out.println("rules.put(\"" + session.resultsFile + "\", new HashMap<Integer, String>());");
		Vector<Trial> transferTrials = new Vector<Trial>();

		for (Trial t : session.trials)
			if (t.isTransfer) {
				transferTrials.add(t);
			}

		for (Trial t : transferTrials) {
//			System.out.println(session.resultsFile + ":" + t.trialNumber);
			String fname = t.choiceStimuli.lastElement().file;
			for (int i = 0; i < t.trialNumber - 1; i++) {
				if (session.trials.get(i).sampleStimuli.firstElement().file.equals(fname)) {
					System.out.println("rules.get(\"" + session.resultsFile + "\").put(" + (t.trialNumber) + ", \"N"
							+ (t.trialNumber - (i + 1)) + "\");");
				}
			}
		}
	}

	@Override
	public void nextTrial(Trial trial) {
	}

	@Override
	public Vector<String> cleanUp() {
		Vector<String> res = new Vector<String>();
		res.add("NA");
		return res;
	}

}

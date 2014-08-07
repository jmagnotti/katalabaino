package mappers;

import java.util.Vector;

import core.Mapper;
import core.session.Session;
import core.trial.Trial;

public class InterTrialIntervalMap extends Mapper {
	private double itiSum, tCount;

	public InterTrialIntervalMap() {
		super("ITI");
	}

	@Override
	public void nextSession(Session session) {
		itiSum = tCount = 0.0;
	}

	@Override
	public Vector<String> cleanUp() {
		resultString = new Vector<String>();
		resultString.add("" + (itiSum / tCount));
		return resultString;
	}

	@Override
	public void nextTrial(Trial trial) {
		tCount++;
		itiSum += trial.intertrialInterval;
		// System.out.println("ITI: " + trial.intertrialInterval);
	}

}

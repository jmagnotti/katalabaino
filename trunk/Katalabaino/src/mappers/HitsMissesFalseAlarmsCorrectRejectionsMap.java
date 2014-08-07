package mappers;

import java.util.Vector;

import core.Mapper;
import core.analysis.Analysis;
import core.session.Session;
import core.trial.Trial;

public class HitsMissesFalseAlarmsCorrectRejectionsMap extends Mapper {
	private int hits, misses, falseAlarms, correctRejections;

	// you'll want to set these based on how the session actually codes
	// absent/present
	public static String TARGET_ABSENT_TRIAL_TYPE = "0";
	public static String TARGET_PRESENT_TRIAL_TYPE = "1";

	public HitsMissesFalseAlarmsCorrectRejectionsMap() {
		super("h" + Analysis.field_delimiter + "m" + Analysis.field_delimiter
				+ "fa" + Analysis.field_delimiter + "cr");

		hits = 0;
		misses = 0;
		falseAlarms = 0;
		correctRejections = 0;
	}

	@Override
	public void nextSession(Session session) {
		hits = 0;
		misses = 0;
		falseAlarms = 0;
		correctRejections = 0;
	}

	@Override
	public void nextTrial(Trial trial) {
		// System.out.println(trial.trialType);
		if (trial.trialType.equalsIgnoreCase(TARGET_PRESENT_TRIAL_TYPE)) {
			if (trial.isCorrect()) {
				hits++;
			} else {
				misses++;
			}
		} else {
			if (trial.isCorrect()) {
				correctRejections++;
			} else {
				falseAlarms++;
			}
		}
	}

	@Override
	public Vector<String> cleanUp() {
		resultString = new Vector<String>();
		resultString.add(hits + Analysis.field_delimiter + misses
				+ Analysis.field_delimiter + falseAlarms
				+ Analysis.field_delimiter + correctRejections);

		return resultString;
	}

}

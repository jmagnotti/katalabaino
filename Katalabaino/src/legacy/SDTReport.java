package legacy;

import java.util.HashMap;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;

public class SDTReport extends Report {
	@SuppressWarnings("unused")
	private double hits, falseAlarms, changeTrials, no_changeTrials;

	private int type;
	public static double sqrt2 = Math.sqrt(2.0);

	public static boolean printed = false;

	private HashMap<String, Double> changeTrialCountByDisplaySize,
			no_changeTrialCountByDisplaySize;
	private HashMap<String, Double> hitsByDisplaySize,
			falseAlarmsByDisplaySize;

	public SDTReport() {
	}

	@Override
	public void preSession(LegacySession session) {
		type = session.reportingMethod;
		hits = 0;
		falseAlarms = 0;

		loadSessionCharacteristics(session);

		changeTrialCountByDisplaySize = new HashMap<String, Double>();
		no_changeTrialCountByDisplaySize = new HashMap<String, Double>();

		hitsByDisplaySize = new HashMap<String, Double>();
		falseAlarmsByDisplaySize = new HashMap<String, Double>();
	}

	@Override
	public void analyzeTrial(Trial trial) {
		String key = trial.sampleDisplaySize + ":" + trial.choiceDisplaySize;

		if (trial.trialType == Trial.CHANGE_TRIAL
				|| trial.trialType == Trial.FORCED_CHOICE_TRIAL
				|| trial.trialType == Trial.DIFFERENT_TRIAL
				|| trial.trialType == Trial.NC_FORCED_CHOICE_TRIAL) {

			if (changeTrialCountByDisplaySize.containsKey(key)) {
				changeTrialCountByDisplaySize.put(key,
						changeTrialCountByDisplaySize.get(key) + 1);
			} else {
				changeTrialCountByDisplaySize.put(key, 1.0);
				hitsByDisplaySize.put(key, 0.0);
			}

			if (trial.isCorrect()) {
				hitsByDisplaySize.put(key, hitsByDisplaySize.get(key) + 1);
			}
		}
		if (trial.trialType == Trial.NO_CHANGE_TRIAL
				|| trial.trialType == Trial.SAME_TRIAL) {
			if (no_changeTrialCountByDisplaySize.containsKey(key)) {
				no_changeTrialCountByDisplaySize.put(key,
						no_changeTrialCountByDisplaySize.get(key) + 1);
			} else {
				no_changeTrialCountByDisplaySize.put(key, 1.0);
				falseAlarmsByDisplaySize.put(key, 0.0);
			}

			if (!trial.isCorrect()) {
				falseAlarmsByDisplaySize.put(key,
						falseAlarmsByDisplaySize.get(key) + 1);
			}
		}
	}

	@Override
	public void postSession() {
		if (!printed) {
			System.out.print("fileName\tgroup\ttimestamp\t");
			for (int i = 0; i < labels.length; i++) {
				// System.out.print("" + labels[i] + "-HR\t" + labels[i] +
				// "-FAR\t" + labels[i] +
				// "-d'"
				System.out.print("" + labels[i] + "-d'" + "\tc" + "\tc'"
						+ (i == labels.length - 1 ? "\n" : "\t"));
			}
			printed = true;
		}

		System.out.print(currentFileName + "\t" + currentGroup + "\t"
				+ currentID);

		if (type == LegacySession.CHANGE_NO_CHANGE
				|| type == LegacySession.SAME_DIFFERENT) {
			for (int i = 0; i < labels.length; i++) {

				double hitrate = hitsByDisplaySize.get(labels[i])
						/ changeTrialCountByDisplaySize.get(labels[i]);

				if (hitrate == 1)
					hitrate = .99;
				else if (hitrate == 0)
					hitrate = .01;

				double false_alarm_rate = falseAlarmsByDisplaySize
						.get(labels[i])
						/ no_changeTrialCountByDisplaySize.get(labels[i]);
				if (false_alarm_rate == 1)
					false_alarm_rate = .99;
				else if (false_alarm_rate == 0)
					false_alarm_rate = .01;

				double dprime, c, cprime;

				NormalDistributionImpl ndi = new NormalDistributionImpl(0, 1);

				try {

					dprime = ndi.inverseCumulativeProbability(hitrate)
							- ndi.inverseCumulativeProbability(false_alarm_rate);
					c = -.5
							* (ndi.inverseCumulativeProbability(hitrate) + ndi
									.inverseCumulativeProbability(false_alarm_rate));

					if (dprime == 0)
						cprime = Double.NaN;
					else
						cprime = c / dprime;
					//
					// if (hitrate == .99)
					// if (false_alarm_rate == .01)
					// System.err.println("GOTCHA: " + dprime);
				} catch (MathException e) {
					e.printStackTrace();
					dprime = Double.NaN;
					c = Double.NaN;
					cprime = Double.NaN;
				}

				// System.out.print("\t" + (hitrate) + "\t" + (false_alarm_rate)
				// + "\t" + dprime);
				System.out.print("\t" + dprime + "\t" + c + "\t" + cprime);
			}
		} else {
			for (int i = 0; i < labels.length; i++) {
				double acc = (hitsByDisplaySize.get(labels[i]) / changeTrialCountByDisplaySize
						.get(labels[i]));
				if (acc == 1)
					acc = .99;
				else if (acc == 0)
					acc = .01;
				MAFCHelper mafc = new MAFCHelper();
				double m;
				m = Double.parseDouble(labels[i].substring(2));
				acc = (double) (int) (100 * acc);
				System.out.print("\t"
						+ mafc.percentCorrectToDprime.get(m).get(acc));
			}
		}
		System.out.println();

	}

	@Override
	public String[] getLabels() {
		return new String[] { "Hit Rate", "False Alarm Rate" };
	}

}

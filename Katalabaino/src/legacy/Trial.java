package legacy;

import java.util.Vector;

import core.Stimulus;

public class Trial {
	public int trialNumber;
	public int trialType;
	public int choicePosition, changePosition, noChangePosition;
	public int choiceColor, changeColor, noChangeColor, changeFromColor;
	public int responseTime, probeDelay, viewTime, interTrialInterval;
	public int sampleDisplaySize;
	public int choiceDisplaySize;

	public int correctColor, correctPosition;

	// this only works for choiceDisplaySize = 2, and will be -1 if
	// choiceDisplaySize != 2
	public int comparisonColor;

	public static final int NO_CHANGE_TRIAL = 0;
	public static final int CHANGE_TRIAL = 1;
	public static final int FORCED_CHOICE_TRIAL = 2;

	public static final int NC_FORCED_CHOICE_TRIAL = 3;
	public static final int SAME_TRIAL = 4;
	public static final int DIFFERENT_TRIAL = 5;

	public static final int CHOOSE_SAME_LINEUP = 8;

	public static final int CHANGE_RESPONSE = -1;
	public static final int NO_CHANGE_RESPONSE = -2;

	public static final int SAME_RESPONSE = -1;
	public static final int DIFFERENT_RESPONSE = -2;

	private boolean correct;

	public Vector<Stimulus> sampleDisplayStimuli;
	public Vector<Stimulus> choiceDisplayStimuli;

	public Trial(int tnum, int ttype, int choiceL, int rt, int sds, int cds,
			int pd, int vt, int iti, Vector<Stimulus> sS, Vector<Stimulus> cS) {
		trialNumber = tnum;
		trialType = ttype;
		choicePosition = choiceL;

		if (choicePosition == 0)
			System.err.println("Caught on t: " + tnum);

		responseTime = rt;
		sampleDisplaySize = sds;
		choiceDisplaySize = cds;
		sampleDisplayStimuli = sS;
		choiceDisplayStimuli = cS;

		probeDelay = pd;
		viewTime = vt;

		interTrialInterval = iti;

		comparisonColor = -1;
		changeFromColor = -1;

		choiceColor = -100;

		// for stroop trials we need to collapse the different interference
		// types for now
		if (trialType > DIFFERENT_TRIAL) {
			trialType = trialType % 10;
		}

		// we need to determine the change position: the position in the choice
		// array that has an
		// image different from the one in the sample display
		if (trialType == FORCED_CHOICE_TRIAL || trialType == CHANGE_TRIAL) {
			boolean found = false;

			for (int i = 0; i < choiceDisplayStimuli.size(); i++) {

				if (choiceDisplayStimuli.get(i).position == choicePosition)
					choiceColor = choiceDisplayStimuli.get(i).colorID;

				for (int j = 0; j < sampleDisplayStimuli.size() && !found; j++) {
					if (choiceDisplayStimuli.get(i).position == sampleDisplayStimuli
							.get(j).position) {
						found = !choiceDisplayStimuli.get(i).file
								.equals(sampleDisplayStimuli.get(j).file);

						if (found) {
							changePosition = choiceDisplayStimuli.get(i).position;
							changeColor = choiceDisplayStimuli.get(i).colorID;
							correctColor = changeColor;

							// System.out.println(choiceDisplayStimuli.get(i).colorID);
							// System.out.println(sampleDisplayStimuli.get(j).colorName);

							changeFromColor = sampleDisplayStimuli.get(j).colorID;
							correctPosition = changePosition;
						}
					}
				}
			}
		}
		// for NC_FORCED_CHOICE and DIFFERENT trials we need to find the
		// position of the item that
		// stayed the same
		else if (trialType == NC_FORCED_CHOICE_TRIAL
				|| trialType == DIFFERENT_TRIAL) {
			boolean found = false;
			for (int i = 0; i < choiceDisplayStimuli.size(); i++) {

				if (choiceDisplayStimuli.get(i).position == choicePosition)
					choiceColor = choiceDisplayStimuli.get(i).colorID;

				for (int j = 0; j < sampleDisplayStimuli.size() && !found; j++) {
					if (choiceDisplayStimuli.get(i).position == sampleDisplayStimuli
							.get(j).position) {
						found = choiceDisplayStimuli.get(i).file
								.equals(sampleDisplayStimuli.get(j).file);
					} else {
						found = choiceDisplayStimuli.get(i).file
								.equals(sampleDisplayStimuli.get(j).file);
					}

					if (found) {
						noChangePosition = choiceDisplayStimuli.get(i).position;
						noChangeColor = choiceDisplayStimuli.get(i).colorID;
						correctColor = noChangeColor;
						correctPosition = noChangePosition;
					}

				}
			}
		}
		// For CHOOSE_SAME_LINEUP the positions are different so
		// correct_position !=
		// change_position, rather we match based on the stimulus file
		if (trialType == CHOOSE_SAME_LINEUP) {
			boolean found = false;
			for (int i = 0; i < choiceDisplayStimuli.size(); i++) {

				if (choiceDisplayStimuli.get(i).position == choicePosition)
					choiceColor = choiceDisplayStimuli.get(i).colorID;

				for (int j = 0; j < sampleDisplayStimuli.size() && !found; j++) {
					found = choiceDisplayStimuli.get(i).file
							.equals(sampleDisplayStimuli.get(j).file);

					if (found) {
						noChangePosition = choiceDisplayStimuli.get(i).position;
						noChangeColor = choiceDisplayStimuli.get(i).colorID;
						correctColor = noChangeColor;
						correctPosition = noChangePosition;
					}
				}
			}
		}

		if (trialType == FORCED_CHOICE_TRIAL) {
			correct = choicePosition == changePosition;

			if (choiceDisplaySize == 2) {
				for (int i = 0; i < 2; i++) {
					if (choiceDisplayStimuli.get(i).position != changePosition)
						comparisonColor = choiceDisplayStimuli.get(i).colorID;
				}
			}
		} else if (trialType == CHANGE_TRIAL || trialType == NO_CHANGE_TRIAL) {
			correct = trialType == CHANGE_TRIAL ? choicePosition == CHANGE_RESPONSE
					: choicePosition == NO_CHANGE_RESPONSE;

			choiceColor = choicePosition == CHANGE_RESPONSE ? CHANGE_TRIAL
					: NO_CHANGE_TRIAL;
			correctColor = trialType;

		} else if (trialType == NC_FORCED_CHOICE_TRIAL) {
			correct = choicePosition == noChangePosition;

			if (choiceDisplaySize == 2) {
				for (int i = 0; i < 2; i++) {
					if (choiceDisplayStimuli.get(i).position != noChangePosition)
						comparisonColor = choiceDisplayStimuli.get(i).colorID;
				}
			}

		} else if (trialType == SAME_TRIAL || trialType == DIFFERENT_TRIAL) {
			correct = trialType == SAME_TRIAL ? choicePosition == SAME_RESPONSE
					: choicePosition == DIFFERENT_RESPONSE;

			choiceColor = choicePosition == SAME_RESPONSE ? SAME_TRIAL
					: DIFFERENT_RESPONSE;
			correctColor = trialType;
		} else if (trialType == CHOOSE_SAME_LINEUP) {

			correct = (choicePosition == noChangePosition);
		}

		// System.out.println("trial: " + trialNumber + "\tChoice: " +
		// choiceposition + "\t"
		// + (correct ? "Correct" : "Wrong\t: " + noChangeposition));

		// set back trialType on stroop trials

		if (trialType == FORCED_CHOICE_TRIAL)
			if (choiceColor == 0)
				System.err.println("Caught on trial: " + trialNumber);

		trialType = ttype;
	}

	public boolean isCorrect() {
		return correct;
	}

}

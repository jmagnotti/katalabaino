package mappers;

import java.util.Vector;

import core.Analysis;
import core.Mapper;
import core.Session;
import core.Trial;

public class IncorrectCorrectionsMap extends Mapper {
	private double totalCorrections, trialCount, atLeastOne;

	public IncorrectCorrectionsMap() {
		super("totI" + Analysis.field_delimiter + "avgIC"
				+ Analysis.field_delimiter + "propIC");

		totalCorrections = trialCount = atLeastOne = 0.0;
	}

	@Override
	public void nextSession(Session session) {
		totalCorrections = trialCount = atLeastOne = 0.0;
	}

	@Override
	public void nextTrial(Trial trial) {
		if (trial.incorrectCorrections > 0) {
			totalCorrections += trial.incorrectCorrections;
			atLeastOne++;
			trialCount++;
		}

	}

	@Override
	public Vector<String> cleanUp() {
		resultString = new Vector<String>();

		resultString.add("" + totalCorrections + Analysis.field_delimiter
				+ (totalCorrections / trialCount) + Analysis.field_delimiter
				+ atLeastOne);

		totalCorrections = trialCount = atLeastOne = 0.0;

		return resultString;
	}
}

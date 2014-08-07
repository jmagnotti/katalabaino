package splitters;

import core.MultiClassRule;
import core.Splitter;
import core.trial.Trial;

/**
 * Splits the data based on the actualViewingTime attribute of {@link Trial}
 */
class ActualViewTimeRule extends MultiClassRule {
	private int start, binSize, cutoff;

	public ActualViewTimeRule(int start, int binSize, int cutoff) {
		this.start = start;
		this.binSize = binSize;
		this.cutoff = cutoff;
	}

	@Override
	public String getClassMembership(Trial trial) {
		int classLabel = 0;

		for (int i = start; i <= cutoff && classLabel == 0; i += binSize) {
			if (trial.actualViewTime <= i) {
				classLabel = 1 + (i - start) / binSize;
			}
		}

		// handle cases past the cutoff
		if (classLabel == 0)
			classLabel = ((int) cutoff / binSize);// + 1;

		return "vt0" + classLabel;
	}
}

/**
 * Uses the {@link ActualViewTimeRule} to generate class memberships based on
 * actualViewingTime. Seeks to provide sensible defaults;
 */
public class ActualViewTimeSplitter extends Splitter {

	public ActualViewTimeSplitter() {
		super(new ActualViewTimeRule(1000, 1000, 9000));
	}

	public ActualViewTimeSplitter(int start, int binSize, int cutoff) {
		super(new ActualViewTimeRule(start, binSize, cutoff));
	}

}

package splitters;

import core.Trial;

/**
 * Splits the data based on the actualViewingTime attribute of {@link Trial}
 */
class ViewTimeRule extends MultiClassRule
{
	private int	start, binSize, cutoff;

	public ViewTimeRule(int start, int binSize, int cutoff)
	{
		this.start = start;
		this.binSize = binSize;
		this.cutoff = cutoff;
	}

	@Override
	public String getClassMembership(Trial trial)
	{
		int classLabel = 0;

		for (int i = start; i <= cutoff && classLabel == 0; i += binSize) {
			if (trial.actualViewTime <= i) {
				classLabel = 1 + (i - start) / binSize;
			}
		}

		// handle cases past the cutoff
		if (classLabel == 0) classLabel = ((int) cutoff / binSize);// + 1;

		return "vt0" + classLabel;
	}
}

/**
 * Uses the {@link ViewTimeRule} to generate class memberships based on actualViewingTime. Seeks to
 * provide sensible defaults;
 */
public class ViewTimeSplitter extends Splitter
{

	public ViewTimeSplitter()
	{
		super(new ViewTimeRule(1000, 1000, 9000));
	}

	public ViewTimeSplitter(int start, int binSize, int cutoff)
	{
		super(new ViewTimeRule(start, binSize, cutoff));
	}

}

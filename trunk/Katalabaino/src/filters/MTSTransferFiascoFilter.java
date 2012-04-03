package filters;

import core.Filter;
import core.Shapes;
import core.Stimulus;
import core.Trial;

/**
 * Handles the issue with Jupiter's Polygon transfer that had some trials with half-polygon
 * half-shape stimuli.
 */
public class MTSTransferFiascoFilter extends Filter
{
	private boolean	printErrors;

	public MTSTransferFiascoFilter()
	{
		this(false);
	}

	public MTSTransferFiascoFilter(boolean printErrors)
	{
		this.printErrors = printErrors;
	}

	@Override
	protected boolean doAllow(Trial trial)
	{
		if (trial.isTransfer) {
			boolean isAllPoly = true;

			for (Stimulus s : trial.choiceStimuli)
				isAllPoly = s.shapeID == Shapes.POLYGON;

			if (!isAllPoly && printErrors) {
				System.out.println("! Trial: " + trial.trialNumber + "\t"
						+ trial.choiceStimuli.get(1).file);
			}

			return isAllPoly;
		}

		return true;

	}
}

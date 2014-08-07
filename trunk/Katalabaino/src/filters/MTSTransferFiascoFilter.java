package filters;

import core.Filter;
import core.analysis.Analysis;
import core.trial.Shapes;
import core.trial.Stimulus;
import core.trial.Trial;

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
				System.out.println("! Trial: " + trial.trialNumber + Analysis.field_delimiter
						+ trial.choiceStimuli.get(1).file);
			}

			return isAllPoly;
		}

		return true;

	}
}

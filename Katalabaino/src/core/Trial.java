package core;

import java.util.Vector;

public class Trial
{
	public Vector<SampleResponse>	sampleResponses, correctionTrialSampleResponses;

	public String					trialType, configuration, response, correctResponse;
	public double					responseTime, probeDelay, intertrialInterval;
	public int						incorrectCorrections, observingResponse, trialNumber, viewTime,
			actualViewTime, sampleSetSize, choiceSetSize, responseLocation, correctLocation,
			interStimulusInterval;

	public boolean					isTransfer;

	public Vector<Stimulus>			sampleStimuli, choiceStimuli;

	public Trial()
	{
		sampleResponses = new Vector<SampleResponse>();
		correctionTrialSampleResponses = new Vector<SampleResponse>();

		sampleStimuli = new Vector<Stimulus>();
		choiceStimuli = new Vector<Stimulus>();
	}

	public int getTransferAsInt()
	{
		return isTransfer ? 1 : 0;
	}

	public int getCorrectAsInt()
	{
		return isCorrect() ? 1 : 0;
	}

	public boolean isCorrect()
	{
		return (incorrectCorrections == 0) && response.equalsIgnoreCase(correctResponse);
	}

	public double getMaxSampleResponseTime()
	{
		double max = -1;

		for (int i = 0; i < sampleResponses.size(); i++) {
			if (max < sampleResponses.get(i).responseTime)
				max = sampleResponses.get(i).responseTime;
		}
		return max;
	}

}

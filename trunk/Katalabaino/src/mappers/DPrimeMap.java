package mappers;

import java.util.Vector;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;

import core.Mapper;
import core.session.Session;
import core.trial.Trial;

public class DPrimeMap extends Mapper {
	private double hits, misses, falseAlarms, correctRejections;

	public static String TARGET_PRESENT = "DIFFERENT";
	public static String TARGET_ABSENT = "SAME";

	public DPrimeMap() {
		super("dp");

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
	public Vector<String> cleanUp() {
		NormalDistributionImpl ndi = new NormalDistributionImpl(0, 1);

		double hitrate = (hits) / (hits + misses);
		double false_alarm_rate = (falseAlarms)
				/ (falseAlarms + correctRejections);
		
		if(hitrate >=1 )
			hitrate = .99;
		else if (hitrate <= 0)
			hitrate = .01;
			
		if(false_alarm_rate >=1 )
			false_alarm_rate = .99;
		else if (false_alarm_rate <= 0)
			false_alarm_rate = .01;
		
		double dp = 0;
		try {
			dp = ndi.inverseCumulativeProbability(hitrate)
					- ndi.inverseCumulativeProbability(false_alarm_rate);

			// double c = -.5
			// * (ndi.inverseCumulativeProbability(hitrate) + ndi
			// .inverseCumulativeProbability(false_alarm_rate));
		} catch (MathException e) {
			e.printStackTrace();
		}

		resultString = new Vector<String>();
		resultString.add("" + dp);

		return resultString;
	}

	@Override
	public void nextTrial(Trial trial) {
		if (trial.trialType.equals(TARGET_PRESENT)) {
			if (trial.isCorrect())
				hits++;
			else
				misses++;
		} else {
			if (trial.isCorrect())
				correctRejections++;
			else
				falseAlarms++;
		}
//		
//		System.out.println(hits + "," + misses + "," + falseAlarms + "," + correctRejections);
		
	}

}

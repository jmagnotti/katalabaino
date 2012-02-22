package splitters;

import java.util.HashMap;
import java.util.Vector;

import core.Trial;

abstract class InterferenceRules
{
	public Vector<Integer>	trials;
	public HashMap<Integer, Integer>	trialToNBack, trialToListPosition;
}

class SetARules extends InterferenceRules
{
	public SetARules()
	{
		trials = new Vector<Integer>();
		trials.add(3);
		trials.add(11);
		trials.add(23);
		trials.add(26);
		trials.add(40);
		trials.add(49);
		trials.add(63);
		trials.add(69);

		trialToNBack = new HashMap<Integer, Integer>();
		trialToListPosition = new HashMap<Integer, Integer>();

		trialToNBack.put(3, 1);
		trialToListPosition.put(3, 5);

		trialToNBack.put(11, 4);
		trialToListPosition.put(11, 1);

		trialToNBack.put(23, 8);
		trialToListPosition.put(23, 5);

		trialToNBack.put(26, 2);
		trialToListPosition.put(26, 1);

		trialToNBack.put(40, 4);
		trialToListPosition.put(40, 5);

		trialToNBack.put(49, 2);
		trialToListPosition.put(49, 5);

		trialToNBack.put(63, 8);
		trialToListPosition.put(63, 1);

		trialToNBack.put(69, 1);
		trialToListPosition.put(69, 1);
	}

}

class SetBRules extends InterferenceRules
{

	SetBRules()
	{
		trials = new Vector<Integer>();
		trials.add(3);
		trials.add(10);
		trials.add(20);
		trials.add(32);
		trials.add(44);
		trials.add(64);
		trials.add(67);
		trials.add(70);

		trialToNBack = new HashMap<Integer, Integer>();
		trialToListPosition = new HashMap<Integer, Integer>();

		trialToNBack.put(3, 1);
		trialToListPosition.put(3, 1);

		trialToNBack.put(10, 2);
		trialToListPosition.put(10, 1);

		trialToNBack.put(20, 4);
		trialToListPosition.put(20, 5);

		trialToNBack.put(32, 8);
		trialToListPosition.put(32, 5);

		trialToNBack.put(44, 4);
		trialToListPosition.put(44, 1);

		trialToNBack.put(64, 8);
		trialToListPosition.put(64, 1);

		trialToNBack.put(67, 1);
		trialToListPosition.put(67, 5);

		trialToNBack.put(70, 2);
		trialToListPosition.put(70, 5);

	}

}

public class ListMemoryInteferenceSplitter extends Splitter
{
	private InterferenceRules	ir;
	public static final int		SET_A	= 0;
	public static final int		SET_B	= 1;

	public ListMemoryInteferenceSplitter(int which)
	{
		super();

		if (SET_A == which)
			ir = new SetARules();
		else
			ir = new SetBRules();
	}

	@Override
	public String getClassMembership(Trial trial)
	{
		if (ir.trials.contains(trial.trialNumber)) {
			return "nb" + ir.trialToNBack.get(trial.trialNumber) + ".lp"
					+ ir.trialToListPosition.get(trial.trialNumber);
		}

		if (trial.trialType.equalsIgnoreCase("SAME"))
			return "same";
		else
			return "diff.tu";
	}
}

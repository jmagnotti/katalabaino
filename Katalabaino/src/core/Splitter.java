package core;

import java.util.HashMap;
import java.util.Vector;

import core.session.Session;
import core.trial.Trial;

/**
 * Create a splitter based on some multiclass rule. then call getSplits to get a
 * labeled map of splits
 */
public class Splitter {
	private Splitter child;
	protected MultiClassRule splitRule;
	HashMap<String, Vector<Trial>> dataSplits;

	protected Splitter() {
	}

	public Splitter(MultiClassRule splitRule) {
		child = null;
		this.splitRule = splitRule;
	}

	public HashMap<String, Vector<Trial>> split(Session session) {
		Vector<Trial> trials = session.trials;
		dataSplits = new HashMap<String, Vector<Trial>>();

		String key = "";
		for (Trial trial : trials) {
			key = getClassMembership(trial);

			if (!dataSplits.containsKey(key)) {
				dataSplits.put(key, new Vector<Trial>());
			}
			dataSplits.get(key).add(trial);
		}

		return dataSplits;
	}

	public void addChild(Splitter splitter) {
		if (child == null)
			child = splitter;
		else
			child.addChild(splitter);
	}

	public void removeChild() {
		if (child != null) {
			child.removeChild();
			child = null;
		}
	}

	public String getClassMembership(Trial trial) {
		if (child != null)
			return splitRule.getClassMembership(trial) + child.getClassMembership(trial);

		return splitRule.getClassMembership(trial);
	}

}

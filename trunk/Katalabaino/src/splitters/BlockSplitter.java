package splitters;

import java.util.Vector;

import core.ComparisonRule;
import core.MultiClassRule;
import core.Splitter;
import core.trial.Trial;

class BlockSplitRule extends MultiClassRule {

	public BlockSplitRule(Vector<ComparisonRule> rules) {
		this.rules = rules;
	}

	@Override
	public String getClassMembership(Trial trial) {
		String classLabel = "";
		for (int i = 0; i < rules.size() && classLabel.equals(""); i++) {
			if (rules.get(i).validate(trial.trialNumber)) {
				if (i >= 9)
					classLabel = "bk_" + (i + 1);
				else
					classLabel = "bk_0" + (i + 1);
			}
		}

		if (classLabel.equals("")) {
			classLabel = "bk_" + rules.size();
		}

		return classLabel + ".";
	}

}

public class BlockSplitter extends Splitter {

	public BlockSplitter(int binSize, int nTrials) {
		Vector<ComparisonRule> rules = new Vector<ComparisonRule>();

		for (int i = binSize; i <= nTrials; i += binSize) {
			rules.add(new ComparisonRule(ComparisonRule.LT_OR_EQ, i));
		}

		splitRule = new BlockSplitRule(rules);
	}

}

package splitters;

import java.util.Vector;

import core.MultiClassRule;
import core.Splitter;
import core.Stimulus;
import core.Trial;

class StimulusInclusionRule extends MultiClassRule
{

	private Vector<String>	includedImages;

	public StimulusInclusionRule(Vector<String> img)
	{
		this.includedImages = img;

		for (int i = 0; i < includedImages.size(); i++) {
			String s = includedImages.get(i);
			includedImages.set(i, s.toLowerCase());
		}

	}

	@Override
	public String getClassMembership(Trial trial)
	{

		String cls = "";

		// what makes a familiar--familiar
		if (includedImages.contains(trial.sampleStimuli.get(0).file.toLowerCase())) {
			cls = "f-";

			boolean novel = false;
			for (Stimulus stim : trial.choiceStimuli)
				novel = novel || (!includedImages.contains(stim.file.toLowerCase()));

			if (novel) {
				cls = cls + "n";

			}
			else {
				cls = cls + "f";
			}
		}
		else {
			cls = "n-";

			boolean novel = true;
			for (Stimulus stim : trial.choiceStimuli)
				novel = novel && (!includedImages.contains(stim.file.toLowerCase()));

			if (novel) {
				cls = cls + "n";
			}
			else {
				cls = cls + "f";
			}
		}

		return cls;
	}
}

public class ItemSplitter extends Splitter
{
	public ItemSplitter(Vector<String> includeImages)
	{
		super(new StimulusInclusionRule(includeImages));
	}

}

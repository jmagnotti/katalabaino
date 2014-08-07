package testing;

import java.util.HashMap;
import java.util.Vector;

import core.Mapper;
import core.session.Session;
import core.trial.Stimulus;
import core.trial.Trial;

public class ImageMap extends Mapper
{

	HashMap<String, String>	images;

	public ImageMap()
	{
		super("images");

		images = new HashMap<String, String>();
	}

	@Override
	public void nextSession(Session session)
	{

	}

	@Override
	public void nextTrial(Trial trial)
	{
		for (Stimulus s : trial.sampleStimuli)
			images.put(s.file, s.file);
		for (Stimulus s : trial.choiceStimuli)
			images.put(s.file, s.file);
	}

	@Override
	public Vector<String> cleanUp()
	{
		resultString = new Vector<String>();

		resultString.addAll(images.keySet());

		return resultString;
	}

}

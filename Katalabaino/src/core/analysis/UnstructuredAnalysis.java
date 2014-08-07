package core.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;

import core.Mapper;
import core.Splitter;
import core.session.Session;
import core.trial.Trial;

/**
 * Sometimes you want to take advantage of the hooks provided by an Analysis but
 * you know better about how the output should be organized.
 */
public class UnstructuredAnalysis extends Analysis {
	public UnstructuredAnalysis(Vector<? extends Session> data) {
		super(data);
	}

	public UnstructuredAnalysis(Vector<? extends Session> data, String outputLocation) {
		super(data);
		try {
			setOutputLocation(new File(outputLocation));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addSplitter(Splitter splitter) {
		System.err.println("No splitting allowed");
	}

	@Override
	public void analyze() {
		for (Mapper map : maps) {
			for (Session session : data) {
				if (filter.doesAllow(session)) {
					map.nextSession(session);
					if (map.needsTrials()) {
						for (Trial trial : session.trials) {
							if (filter.doesAllow(trial)) {
								map.nextTrial(trial);
							}
						}
					}
				}
			}
			Vector<String> results = map.cleanUp();
			for (String string : results)
				output.println(string);
		}
		if (!System.out.equals(output))
			output.close();
	}
}

package testing;

import java.util.Vector;

import mappers.Mapper;
import splitters.Splitter;
import core.Analysis;
import core.Session;
import core.Trial;

public class MultiSessionHDAnalysis extends Analysis
{
	public MultiSessionHDAnalysis(Vector<? extends Session> data)
	{
		super(data);
	}

	@Override
	public void addSplitter(Splitter splitter)
	{
		System.err.println("No splitting allowed");
	}

	@Override
	public void analyze()
	{
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
				System.out.println(string);
		}
	}

}

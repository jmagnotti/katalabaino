package core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import mappers.Mapper;
import splitters.Splitter;
import filters.Filter;
import filters.PassThroughFilter;

public class Analysis
{

	protected Vector<? extends Session>				data;

	protected Filter								filter;
	protected Splitter								splitter;
	protected Vector<Mapper>						maps;

	public HashMap<String, Vector<Vector<String>>>	results;

	public static String							SPACE_DELIMITER	= "\t";

	protected int									maxLevel;

	protected Vector<String>						keys;

	protected Analysis()
	{}

	public Analysis(Vector<? extends Session> data)
	{
		maps = new Vector<Mapper>();
		this.data = data;
		filter = new PassThroughFilter();
		splitter = null;
	}

	public void setData(Vector<? extends Session> data)
	{
		this.data = data;
	}

	public void addMap(Mapper map)
	{
		maps.add(map);
	}

	public void setFilter(Filter filter)
	{
		this.filter = filter;
	}

	public void analyze()
	{
		// put this here so multiple calls to analyze will work properly
		keys = new Vector<String>();
		results = new HashMap<String, Vector<Vector<String>>>();
		maxLevel = 0;

		for (Mapper map : maps) {
			int currentLevel = -1;

			for (int currentSession = 0; currentSession < data.size(); currentSession++) {
				Session session = data.get(currentSession);

				if (filter.doesAllow(session)) {
					currentLevel++;
					if (splitter == null || !map.allowSplits()) {
						map.nextSession(session);
						if (map.needsTrials()) {
							for (Trial trial : session.trials) {
								if (filter.doesAllow(trial)) map.nextTrial(trial);
							}
						}
						if (!keys.contains(map.toString())) {
							keys.add(map.toString());
							results.put(map.toString(), new Vector<Vector<String>>());
						}

						results.get(map.toString()).add(map.cleanUp());

						maxLevel = Math.max(maxLevel, results.get(map.toString()).size());
					}
					else {
						HashMap<String, Vector<Trial>> splitData = splitter.split(session.trials);

						String[] keyset = new String[splitData.keySet().size()];
						splitData.keySet().toArray(keyset);
						// Arrays.sort(keyset);

						for (String key : keyset) {

							String fullkey = map.toString(key);

							Vector<Trial> trials = splitData.get(key);
							map.nextSession(session);
							for (Trial trial : trials) {
								if (filter.doesAllow(trial)) map.nextTrial(trial);
							}

							if (!keys.contains(fullkey)) {
								keys.add(fullkey);
								results.put(fullkey, new Vector<Vector<String>>());

								// System.out.println(results.get(key+map.toString()).size());
							}

							// pad down the column if need be
							// this comes into play when sessions are missing entries
							for (int j = results.get(fullkey).size(); j < currentLevel; j++) {
								// System.err.println("padding " + key + map.toString());
								results.get(fullkey).add(new Vector<String>());
								results.get(fullkey).lastElement().add(".");

							}

							results.get(fullkey).add(map.cleanUp());

							maxLevel = Math.max(maxLevel, results.get(fullkey).size());
						}
					}
				}
			}
		}

		String[] keyset = new String[keys.size()];
		keys.toArray(keyset);
		Arrays.sort(keyset);

		System.out.print(keyset[0]);
		for (int i = 1; i < keyset.length; i++) {
			System.out.print(SPACE_DELIMITER + keyset[i]);
		}
		System.out.println();

		for (int level = 0; level < maxLevel; level++) {
			for (String key : keyset) {
				if (results.get(key).size() > level) {
					for (String val : results.get(key).get(level)) {

						// need to special case the first element in the row (and make sure it is
						// the first element of any multi-item results
						if (key.equals(keyset[0]) && val.equals(results.get(key).get(level).get(0))) {
							System.out.print(val);
						}
						else {
							System.out.print(SPACE_DELIMITER + val);
						}
					}
				}
				// we need to pad out the columns in case we have jagged arrays
				else {
					int padsize = results.get(key).get(0).size();

					if (padsize < 1) padsize = 1;

					for (int i = 0; i < padsize; i++)
						System.out.print(SPACE_DELIMITER + ".");
				}
			}
			System.out.println();
		}

	}

	public void addSplitter(Splitter splitter)
	{
		if (this.splitter != null)
			this.splitter.addChild(splitter);
		else
			this.splitter = splitter;
	}

	public void setSplitter(Splitter splitter)
	{
		this.splitter = splitter;
	}

	public void addFilter(Filter filter)
	{
		this.filter.addChild(filter);
	}

	public void clearSplitters()
	{
		splitter = null;
	}

	public void clearMaps()
	{
		maps.clear();
	}

	public void clearFilters()
	{
		filter = new PassThroughFilter();
	}
}

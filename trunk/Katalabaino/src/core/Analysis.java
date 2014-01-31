package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import filters.PassThroughFilter;

public class Analysis {
	protected Vector<? extends Session> data;

	protected Filter filter;
	protected Splitter splitter;
	protected Vector<Mapper> maps;

	public HashMap<String, Vector<Vector<String>>> results;

	// this is static because maps that need to shoe-horn extra values need it
	// #TODO shore up Mappers so they don't need access to this value. May
	// require configuring mappers for multiple outputs. One possibility is to
	// have a mapper that enforces a splitter? That way Analyze knows to expect
	// multiple values? Alternatively, we could have GetCurrentDelimiter
	// function that returns a private (but static) instance of field_delimiter.
	// Calls to set the delimiter update this static value?
	public static String field_delimiter = "\t";
	private PrintStream output;

	protected int maxLevel;

	protected Vector<String> keys;

	// It is nonsensical to create an Analysis without any data, right?
	// It might be useful to have an analysis sitting around with all its
	// maps/filters/splitters and then just pass data to it?
	// In this case, we would just initialize the analysis with dataset 1, then
	// iterate using calls to setData
	protected Analysis() {
	}

	public Analysis(Vector<? extends Session> data) {
		maps = new Vector<Mapper>();
		this.data = data;
		filter = new PassThroughFilter();
		splitter = null;

		// by default we print to the console
		output = System.out;
	}

	public void setOutputLocation(File file) throws FileNotFoundException {
		setOutputLocation(new PrintStream(file));
	}

	public void setOutputLocation(PrintStream out) {
		output = out;
	}

	public void setData(Vector<? extends Session> data) {
		this.data = data;
	}

	public void addMap(Mapper map) {
		maps.add(map);
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public void analyze() {
		// put this here so multiple calls to analyze will work properly
		keys = new Vector<String>();
		results = new HashMap<String, Vector<Vector<String>>>();
		maxLevel = 0;

		for (Mapper map : maps) {
			int currentLevel = -1;

			for (int currentSession = 0; currentSession < data.size(); currentSession++) {

				// System.out.println(data.get(currentSession).resultsFile);

				Session session = data.get(currentSession);

				if (filter.doesAllow(session)) {
					currentLevel++;
					if (splitter == null || !map.allowSplits()) {
						map.nextSession(session);
						if (map.needsTrials()) {
							for (Trial trial : session.trials) {
								if (filter.doesAllow(trial))
									map.nextTrial(trial);
							}
						}
						if (!keys.contains(map.toString())) {
							keys.add(map.toString());
							results.put(map.toString(), new Vector<Vector<String>>());
						}

						results.get(map.toString()).add(map.cleanUp());

						maxLevel = Math.max(maxLevel, results.get(map.toString()).size());
					} else {
						// System.out.println("Splitting: " + session.id);
						HashMap<String, Vector<Trial>> splitData = splitter.split(session.trials);

						String[] keyset = new String[splitData.keySet().size()];
						splitData.keySet().toArray(keyset);
						// Arrays.sort(keyset);

						for (String key : keyset) {

							String fullkey = map.toString(key);

							Vector<Trial> trials = splitData.get(key);
							map.nextSession(session);
							for (Trial trial : trials) {
								if (filter.doesAllow(trial))
									map.nextTrial(trial);
							}

							if (!keys.contains(fullkey)) {
								keys.add(fullkey);
								results.put(fullkey, new Vector<Vector<String>>());

								// System.out.println(results.get(key+map.toString()).size());
							}

							// pad down the column if need be
							// this comes into play when sessions are missing
							// entries
							for (int j = results.get(fullkey).size(); j < currentLevel; j++) {
								// System.err.println("padding " + key +
								// map.toString());
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

		// if we're printing to the console, add some separation
		if (System.out.equals(output))
			output.println("\n---\n");

		if (keyset.length > 0)
			output.print(keyset[0]);
		for (int i = 1; i < keyset.length; i++) {
			output.print(field_delimiter + keyset[i]);
		}
		output.println();

		for (int level = 0; level < maxLevel; level++) {
			for (String key : keyset) {
				if (results.get(key).size() > level) {
					for (String val : results.get(key).get(level)) {

						// need to special case the first element in the row
						// (and make sure it is the first element of any
						// multi-item results
						if (key.equals(keyset[0]) && val.equals(results.get(key).get(level).get(0))) {
							output.print(val);
						} else {
							output.print(field_delimiter + val);
						}
					}
				}
				// we need to pad out the columns in case we have jagged arrays
				else {
					int padsize = results.get(key).get(0).size();

					if (padsize < 1)
						padsize = 1;

					for (int i = 0; i < padsize; i++)
						output.print(field_delimiter + ".");
				}
			}
			output.println();
		}

		if (!System.out.equals(output))
			output.close();

		System.out.println("Done.");
	}

	public void addSplitter(Splitter splitter) {
		if (this.splitter != null)
			this.splitter.addChild(splitter);
		else
			this.splitter = splitter;
	}

	public void setSplitter(Splitter splitter) {
		this.splitter = splitter;
	}

	public void addFilter(Filter filter) {
		this.filter.addChild(filter);
	}

	public void clearSplitters() {
		splitter = null;
	}

	public void clearMaps() {
		maps.clear();
	}

	public void clearFilters() {
		filter = new PassThroughFilter();
	}

	public void setFieldDelimiter(String delimiter) {
		field_delimiter = delimiter;
	}

	/**
	 * Clears all splitters, maps, and filters. Does not change data
	 */
	public void clearAll() {
		clearSplitters();
		clearMaps();
		clearFilters();
	}
}

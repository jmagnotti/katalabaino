package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Vector;

public class StringMatrix implements Comparable<StringMatrix> {

	private Vector<Vector<String>> mat;

	private StringMatrix() {
		mat = new Vector<Vector<String>>();
	}

	public StringMatrix(int r, int c) {
		mat = new Vector<Vector<String>>();
		for (int i = 0; i < r; i++) {
			mat.add(new Vector<String>());
			for (int j = 0; j < c; j++)
				mat.lastElement().add("");
		}
	}

	public static StringMatrix Build(File from) throws IOException {
		return Build(from, ",");
	}

	public static StringMatrix Build(File from, String delimiter)
			throws IOException {
		StringMatrix sm = new StringMatrix();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(from)));

		while (br.ready()) {
			String line = br.readLine();
			sm.mat.add(new Vector<String>());
			sm.mat.lastElement().addAll(Arrays.asList(line.split(delimiter)));
		}

		br.close();

		return sm;
	}

	/**
	 * Adds b to a. Note the a is modified by this call AND returned by this
	 * call to allow function chaining
	 */
	public StringMatrix join(StringMatrix b) {
		if (this.compareTo(b) < 0) {
			stretch(b.getDim());
		}

		if (this.compareTo(b) > 0) {
			b.stretch(getDim());
		}

		// now that we are matched in the number of rows, we want to add the
		// "columns"
		for (int i = 0; i < getDim()[0]; i++) {
			mat.get(i).addAll(b.mat.get(i));
		}

		return this;
	}

	/**
	 * Add rows to match the number of rows in dim, pad the columns with empty
	 * strings
	 */
	private void stretch(Integer[] dim) {
		while (dim[0] > getDim()[0]) {
			mat.add(new Vector<String>());
			for (int i = 0; i < getDim()[1]; i++) {
				mat.lastElement().add("");
			}
		}
	}

	/**
	 * prints to file with comma as field delimiter
	 * 
	 * @param to
	 */
	public void toFile(File to) throws IOException {
		toFile(to, ",");
	}

	public void toFile(File to, String delimiter) throws IOException {
		FileWriter fw = new FileWriter(to);
		for (int row = 0; row < mat.size(); row++) {
			for (int col = 0; col < mat.get(row).size(); col++) {
				if (col > 0)
					fw.write(delimiter);

				fw.write(mat.get(row).get(col));
			}
			fw.write(System.lineSeparator());
		}

		fw.close();
	}

	public Integer[] getDim() {
		return (new Integer[] { mat.size(), mat.get(0).size() });
	}

	@Override
	public int compareTo(StringMatrix o) {
		if (getDim()[0] > o.getDim()[0])
			return 1;

		if (getDim()[0] < o.getDim()[0])
			return -1;

		return 0;
	}
}

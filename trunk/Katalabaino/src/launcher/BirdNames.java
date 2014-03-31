package launcher;

public class BirdNames {

	public static String[] MAGPIES = { "redx01", "red004", "red", "yellow", "blueA3", "blueA0", "blueA", "blue" };
	public static String[] JAYS = { "caesar", "hans", "henry", "kermit", "marvin", "oscar", "theseus" };
	public static String[] NUTCRACKERS = {"bismark", "fido", "george", "howie", "krusty", "lance", "reorx", "sid", "tan"};

	/**
	 * Returns the String UNKNOWN if it can't match the name from the follow
	 * 
	 * @param fileName
	 * @return
	 */
	public static String GetNameFromFileName(String fileName, String[] names) {
		String name = "UNKNOWN";
		boolean match = false;
		int idx = 0;
		while (idx < names.length && !match) {
			match = fileName.toLowerCase().startsWith(names[idx].toLowerCase());
			if (match)
				name = names[idx];
			idx++;
		}
		return name;
	}

	public static void main(String[] args) {
		System.out.println(GetNameFromFileName("BlueA38tr_1027.tr", MAGPIES));
	}
}

package setup;

import java.io.File;

import core.constants.BirdNames;

public class MakeSDFolders {

	private static void buildFolders(String dir) {
		int[] setsizes = { 8, 16, 32, 64, 128, 256, 512, 1024 };
		String folders[] = { "train", "tran" };

		for (Integer setsize : setsizes) {
			for (String folder : folders) {
				File f = new File(dir + setsize + folder);
				
				System.out.println("Trying: " + f.getAbsolutePath());
				if (!f.exists()) {
					f.mkdirs();
				}
			}
		}
	}

	public static void main(String[] args) {

//		String[] names = { "redx01", "red004", "red", "yellow", "blueA3", "blueA0", "blueA", "blue" };
		//String [] names = BirdNames.NUTCRACKERS;
		String [] names = BirdNames.JAYS;
		for (String name : names)
			buildFolders("Z:/warehouse/jay/" + name + "/");
				
	}
}

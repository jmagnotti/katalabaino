package testing;

import java.io.File;
import java.util.Arrays;

public class PrintAnalysisOptions
{

	private static String prettyPrint(String s)
	{
		String result = "";

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (Character.isUpperCase(c)) {
				result = result + "_" + c;
			}
			else {
				result = result + c;
			}
		}

		// return result.toLowerCase().replaceAll("_map", "");
		return result.toLowerCase().replaceAll("_splitter", "");

	}

	public static void printMethod(String t, String f)
	{
		System.out.println("public void REFL_" + t + prettyPrint(f)
				+ "() {\n\tanalysis.addSplitter(new " + f + "());\n}");
	}

	public static void main(String[] args)
	{

		File[] files = new File("/Users/jmagnotti/Workspace/Katalabaino/src/sessions/").listFiles();

		Arrays.sort(files);

		for (File file : files) {
			if (file.getName().endsWith(".java"))
//				printMethod("split", file.getName().substring(0, file.getName().length() - 5));
				System.out.println("sessionTypes.add(\"" + file.getName().substring(0, file.getName().length()-5) + "\");");
		}
	}
}

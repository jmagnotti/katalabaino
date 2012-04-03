package analyses;

import java.io.File;
import java.util.Vector;

import mappers.PercentCorrectMap;
import mappers.SessionInformationMap;
import sessions.PseudoSDSession;
import splitters.ItemSplitter;
import core.Analysis;
import core.FileTypeConverter;
import core.Session;
import core.SessionFactory;

@SuppressWarnings("unused")
public class PseudoSDAnalysis {
	public static void main(String[] args) throws Exception {
		String dir = "C:/information/pseudo/32acquisition/";
		String bird = "rocksteady";
		String workDir = dir + bird + "/";

		// call this once each time you add new results files
		FileTypeConverter.CreateZipFileFromDirectory(workDir, bird,
				new PseudoSDSession());

		File zipFile = new File(workDir + bird + ".dbo");

		Vector<Session> sessions = SessionFactory.BuildSessions(
				new PseudoSDSession(), zipFile);

		Analysis analysis = new Analysis(sessions);

		 Vector<String> includedImages = new Vector<String>();
		// original 8-item set
		 includedImages.add("T310.tga");
		 includedImages.add("T301.tga");
		 includedImages.add("T197.tga");
		 includedImages.add("T244.tga");
		 includedImages.add("T135.tga");
		 includedImages.add("T415.tga");
		 includedImages.add("T13.tga");
		 includedImages.add("T76.tga");
		// items learned in 16-item set
		// includedImages.add("T114.tga");
		// includedImages.add("T119.tga");
		// includedImages.add("T384.tga");
		// includedImages.add("T423.tga");
		// includedImages.add("T186.tga");
		// includedImages.add("T61.tga");
		// includedImages.add("T56.tga");
		// includedImages.add("T342.tga");
		// items learned in 32-item set
		// includedImages.add("t261.tga");
		// includedImages.add("t42.tga");
		// includedImages.add("t28.tga");
		// includedImages.add("t324.tga");
		// includedImages.add("t5.tga");
		// includedImages.add("t205.tga");
		// includedImages.add("t354.tga");
		// includedImages.add("t369.tga");
		// includedImages.add("t267.tga");
		// includedImages.add("t411.tga");
		// includedImages.add("t356.tga");
		// includedImages.add("t159.tga");
		// includedImages.add("t403.tga");
		// includedImages.add("t26.tga");
		// includedImages.add("t300.tga");
		// includedImages.add("t264.tga");
		// items learned in 64-item set

		analysis.addMap(new SessionInformationMap());
		// analysis.addMap(new AverageRTMap());
		// analysis.addMap(new CPInfoMap());
		analysis.addMap(new PercentCorrectMap());
		analysis.addSplitter(new ItemSplitter(includedImages));
		// analysis.addSplitter(new TrialTypeSplitter());
//		analysis.addSplitter(new PseudoTrueSplitter());
		// analysis.addSplitter(new BaselineTransferSplitter());
		// analysis.addFilter(new CorrectTrialsOnlyFilter());
		// analysis.addFilter(new ReactionTimeFilter(new
		// ComparisonRule(ComparisonRule.LT_OR_EQ, 6000)));
		// analysis.addMap(new CountMap());
		// execute.
		analysis.analyze();
	}
}
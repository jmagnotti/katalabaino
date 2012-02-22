package analyses;

import java.io.File;
import java.util.Vector;

import mappers.AccuracyMap;
import mappers.SessionInfoMap;
import sessions.MovementSession;
//import splitters.ItemSplitter;
import core.Analysis;
import core.FileTypeConverter;
import core.Session;
import core.SessionFactory;

public class MoveAnalysis {

public static void main(String[] args) throws Exception
{
	String dir = "C:/information/movement/all/";
	String bird = "raphael";
	String workDir = dir + bird + "/";

	FileTypeConverter.CreateZipFileFromDirectory(workDir, bird, new MovementSession());
	File zipFile = new File(workDir + bird + ".dbo");
	Vector<Session> sessions = SessionFactory.BuildSessions(new MovementSession(), zipFile);

	Analysis analysis = new Analysis(sessions);


	analysis.addMap(new SessionInfoMap());
	analysis.addMap(new AccuracyMap());
	//analysis.addSplitter(new ItemSplitter(includedImages));
	// analysis.addMap(new AverageRTMap());
	// analysis.addMap(new SamplePecksAverageMap());
	// analysis.addMap(new AverageFR_RTMap());
	analysis.analyze();
}
}

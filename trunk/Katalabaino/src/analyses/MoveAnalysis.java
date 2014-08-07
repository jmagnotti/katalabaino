package analyses;

import java.io.File;
import java.util.Vector;

import mappers.PercentCorrectMap;
import mappers.SessionInformationMap;
import sessions.MovementSession;
//import splitters.ItemSplitter;
import core.analysis.Analysis;
import core.session.Session;
import core.session.SessionFactory;
import file.FileTypeConverter;

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


	analysis.addMap(new SessionInformationMap());
	analysis.addMap(new PercentCorrectMap());
	//analysis.addSplitter(new ItemSplitter(includedImages));
	// analysis.addMap(new AverageRTMap());
	// analysis.addMap(new SamplePecksAverageMap());
	// analysis.addMap(new AverageFR_RTMap());
	analysis.analyze();
}
}

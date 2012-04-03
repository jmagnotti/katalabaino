package analyses;

import java.io.File;
import java.util.Vector;

import mappers.PercentCorrectMap;
import mappers.SessionInformationMap;
import sessions.HumanListMemorySession;
import splitters.CorrectPositionSplitter;
import splitters.ListMemoryInteferenceSplitter;
import core.Analysis;
import core.Session;
import core.SessionFactory;
import filters.SessionNameFilter;

public class ListMemoryAnalysis
{
	public static void main(String[] args) throws Exception
	{
		// String dir = "/Users/jmagnotti/Downloads/pi/";
		String dir = "Y:/Downloads/pi/";
		String bird = "results";
		String workDir = dir + bird + "/";

		// FileTypeConverter.CreateZipFileFromDirectory(workDir, bird, new
		// HumanListMemorySession());

		File zipFile = new File(workDir + bird + ".dbo");

		Vector<Session> sessions = SessionFactory.BuildSessions(new HumanListMemorySession(),
				zipFile);

		Analysis analysis = new Analysis(sessions);

		analysis.addMap(new SessionInformationMap());
		analysis.addMap(new PercentCorrectMap());

		analysis.addFilter(new SessionNameFilter("_B"));
		analysis.addSplitter(new ListMemoryInteferenceSplitter(ListMemoryInteferenceSplitter.SET_B));
		analysis.addSplitter(new CorrectPositionSplitter());

		analysis.analyze();
	}
}

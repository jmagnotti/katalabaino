package analyses;

import java.io.File;
import java.util.Vector;

import mappers.AccuracyMap;
import mappers.AverageFRMap;
import mappers.AverageFR_RTMap;
import mappers.ObservingResponseInfoMap;
import mappers.SessionInfoMap;
import mappers.ViewTimeAbortsMap;
import sessions.YN_CDSession;
import splitters.*;
import core.Analysis;
import core.FileTypeConverter;
import core.Session;
import core.SessionFactory;

public class YN_CDAnalysis
{
	public static void main(String[] args) throws Exception
	{
		String dir = "Y:/warehouse/YN_cd/acquisition/";
		String bird = "curly";
		String workDir = dir + bird + "/";

		// FileTypeConverter.CreateZipFileFromDirectory(workDir, bird, new YN_CDSession());

		File zipFile = new File(workDir + bird + ".dbo");

		Vector<Session> sessions = SessionFactory.BuildSessions(new YN_CDSession(), zipFile);

		Analysis analysis = new Analysis(sessions);

		analysis.addMap(new SessionInfoMap());
		// analysis.addMap(new AccuracyMap());
		// analysis.addMap(new ViewTimeAbortsMap());
		// analysis.addMap(new AverageRTMap());
		// analysis.addMap(new AverageFRMap());
		analysis.addMap(new ObservingResponseInfoMap());

		// analysis.addSplitter(new SampleSetSizeSplitter());
		// analysis.addSplitter(new TrialTypeSplitter());
		// analysis.addSplitter(new ProbeDelaySplitter());
		// analysis.addSplitter(new ConfigurationSplitter("icm"));

		// analysis.addSplitter(new CorrectPositionSplitter());

		// analysis.addSplitter(new ViewTimeAbortSplitter());

		analysis.analyze();
	}

}

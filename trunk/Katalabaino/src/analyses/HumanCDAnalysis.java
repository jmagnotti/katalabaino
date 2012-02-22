package analyses;

import java.io.File;
import java.util.Vector;

import mappers.CountMap;
import mappers.HMFACR_Map;
import mappers.SessionInfoMap;
import sessions.HumanCDSession;
import splitters.TrialTypeSplitter;
import core.Analysis;
import core.Session;
import core.SessionFactory;

public class HumanCDAnalysis
{
	public static void main(String[] args) throws Exception
	{

		File zipFile = new File("Y:/Desktop/dissertation_files/cd_data/restemp/pick-change.dbo");
		Vector<Session> sessions = SessionFactory.BuildSessions(new HumanCDSession(), zipFile);

		Analysis analysis = new Analysis(sessions);

		analysis.addMap(new SessionInfoMap());
		// analysis.addMap(new CountCorrectMap());
		analysis.addMap(new CountMap());
		analysis.addFilter(new LookForTrialDuplicatesFilter());
		// analysis.addMap(new AccuracyMap());
		// analysis.addMap(new AverageRTMap());
		Analysis.SPACE_DELIMITER = "\t";

		// HMFACR_Map.TARGET_ABSENT_TRIAL_TYPE = "Diff";
		// HMFACR_Map.TARGET_PRESENT_TRIAL_TYPE = "Same";

		// analysis.addMap(new HMFACR_Map());

		// analysis.addSplitter(new ChoiceSetSizeSplitter());
		// analysis.addSplitter(new TrialTypeSplitter());
		// analysis.addSplitter(new SampleSetSizeSplitter());

		// analysis.addFilter(new SessionNameFilter("b2."));

		analysis.analyze();
	}
}

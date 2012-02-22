package analyses;

import java.io.File;
import java.util.Vector;

import sessions.FC_CDSession;
import core.Session;
import core.SessionFactory;
import core.Trial;

public class FC_CDRegression
{

	public static void print(String... vals)
	{
		System.out.print(vals[0]);
		for (int i = 1; i < vals.length; i++)
			System.out.print("," + vals[i]);

		System.out.println();
	}

	public static void main(String[] args) throws Exception
	{
		File file = new File("Y:/warehouse/FC_cd/ds7to10/brace/brace.dbo");

		Vector<Session> sessions = SessionFactory.BuildSessions(new FC_CDSession(), file);

		print("SID", "tNum", "Correct", "VT", "PD", "SSS", "VTxPD", "VTxSSS", "PDxSSS", "VTxPDxSSS");

		for (Session session : sessions) {

			if (Integer.parseInt(session.id) > 750)
				for (Trial t : session.trials) {
					if (t.sampleSetSize > 1)
						print(session.id, "" + t.trialNumber, "" + t.getCorrectAsInt(), ""
								+ t.actualViewTime, "" + t.probeDelay, "" + t.sampleSetSize, ""
								+ (t.actualViewTime * t.probeDelay), ""
								+ (t.actualViewTime * t.sampleSetSize), ""
								+ (t.probeDelay * t.sampleSetSize), ""
								+ (t.actualViewTime * t.probeDelay * t.sampleSetSize));
				}
		}
	}
}

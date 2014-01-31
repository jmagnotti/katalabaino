package analyses;

import java.io.File;
import java.util.Vector;

import core.CombinedAnalysis;
import core.FileTypeConverter;
import core.Session;
import core.SessionFactory;

public abstract class TypicalAnalysis implements Runnable {
	protected String directory, subject;
	protected CombinedAnalysis analysis;
	protected final Session dataType;

	protected TypicalAnalysis(String dir, String subj, Session session) {
		directory = dir;
		subject = subj;
		dataType = session;
	}

	@Override
	public void run() {
		String workDir = directory + subject + "/";

		try {
			FileTypeConverter.CreateZipFileFromDirectory(workDir, subject,
					dataType);
			File zipFile = new File(workDir + subject + ".dbo");
			Vector<Session> sessions = SessionFactory.BuildSessions(dataType,
					zipFile);
			analysis = new CombinedAnalysis(sessions, workDir + subject
					+ "_output.csv");

			// let the sub-class know that everything is ready for them to start
			// processing
			do_analyze();

			analysis.accumulate(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Concrete children should put all of there analysis code in this method.
	 * Note that the parent will take care of creating the CombinedAnalysis
	 * object and calling accumulate() on it.
	 */
	public abstract void do_analyze();

}

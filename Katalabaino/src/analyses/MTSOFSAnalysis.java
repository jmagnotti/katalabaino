package analyses;

import java.io.File;
import java.util.Vector;

import mappers.PercentCorrectMap;
import mappers.CorrectionProcedureStatusMap;
import mappers.CountMap;
import mappers.SessionInformationMap;
import sessions.MTSOFSSession;
import splitters.BlockSplitter;
import core.Analysis;
import core.FileTypeConverter;
import core.Filter;
import core.Session;
import core.SessionFactory;
import filters.CorrectionProcedureFilter;

public class MTSOFSAnalysis
	{
		public static void main(String[] args) throws Exception
		{
			String dir = "C:/information/mtsofs/3item/";
			String bird = "leo";
			String workDir = dir + bird + "/";
	
			FileTypeConverter.CreateZipFileFromDirectory(workDir, bird, new MTSOFSSession());
			File zipFile = new File(workDir + bird + ".dbo");
			Vector<Session> sessions = SessionFactory.BuildSessions(new MTSOFSSession(), zipFile);
	
			Analysis analysis = new Analysis(sessions);
			
			Analysis.SPACE_DELIMITER = "\t";
			
			analysis.addMap(new SessionInformationMap());
			analysis.addMap(new PercentCorrectMap());
			analysis.addSplitter(new BlockSplitter(8, 96));
//			analysis.addMap(new CPInfoMap());
			analysis.addFilter(new CorrectionProcedureFilter(0));
			
//			analysis.addSplitter(new ItemSplitter(includedImages));
			// analysis.addMap(new AverageRTMap());
			// analysis.addMap(new SamplePecksAverageMap());
			// analysis.addMap(new AverageFR_RTMap());
			analysis.analyze();
		}

		}
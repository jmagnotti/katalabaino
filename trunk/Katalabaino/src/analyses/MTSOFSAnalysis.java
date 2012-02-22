package analyses;

import java.io.File;
import java.util.Vector;

import mappers.AccuracyMap;
import mappers.CPInfoMap;
import mappers.CountMap;
import mappers.SessionInfoMap;
import sessions.MTSOFSSession;
import splitters.BlockSplitter;
import core.Analysis;
import core.FileTypeConverter;
import core.Session;
import core.SessionFactory;
import filters.CPFilter;
import filters.Filter;

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
			
			analysis.addMap(new SessionInfoMap());
			analysis.addMap(new AccuracyMap());
			analysis.addSplitter(new BlockSplitter(8, 96));
//			analysis.addMap(new CPInfoMap());
			analysis.addFilter(new CPFilter(0));
			
//			analysis.addSplitter(new ItemSplitter(includedImages));
			// analysis.addMap(new AverageRTMap());
			// analysis.addMap(new SamplePecksAverageMap());
			// analysis.addMap(new AverageFR_RTMap());
			analysis.analyze();
		}

		}
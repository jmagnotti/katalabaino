package core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import mappers.CorrectionProcedureStatusMap;
import mappers.CountCorrectMap;
import mappers.CountMap;
import mappers.IncorrectCorrectionsMap;
import mappers.MeanActualViewTimeMap;
import mappers.MeanResponseTimeMap;
import mappers.MeanSampleCompletionResponseTimeMap;
import mappers.MeanSampleResponseMap;
import mappers.MedianResponseTimeMap;
import mappers.PercentCorrectMap;
import mappers.SessionInformationMap;

import org.xml.sax.SAXException;

import splitters.BaselineTransferSplitter;
import splitters.ChoiceSetSizeSplitter;
import splitters.ConfigurationSplitter;
import splitters.CorrectIncorrectSplitter;
import splitters.CorrectPositionSplitter;
import splitters.ItemConfigurationSplitter;
import splitters.ProbeDelaySplitter;
import splitters.PseudoTrueSplitter;
import splitters.SampleColorSplitter;
import splitters.SampleSetSizeSplitter;
import splitters.TrialTypeSplitter;
import filters.CorrectTrialsOnlyFilter;
import filters.CorrectionProcedureFilter;
import filters.ResponseTimeFilter;
import filters.SampleResponseResponseTimeFilter;

public class AnalysisBuilder implements Runnable
{
	public Analysis							analysis;

	private static HashMap<String, Method>	methods		= null;
	public static Vector<String>			sortedNames	= null;
	// public static AnalysisBuilder builder = null;

	public static Vector<String>			mappers, splitters, filters;

	public Vector<String>					sessionTypes;

	public AnalysisBuilder()
	{
		methods = new HashMap<String, Method>();
		Method[] meths = AnalysisBuilder.class.getMethods();

		for (Method m : meths)
			methods.put(m.getName(), m);

		sortedNames = new Vector<String>();

		for (String methodName : methods.keySet()) {
			if (methodName.startsWith("REFL_")) {
				sortedNames.add(methodName.substring(5));
			}
		}

		Collections.sort(sortedNames);
		mappers = new Vector<String>();
		splitters = new Vector<String>();
		filters = new Vector<String>();

		// now add the sorted names into the appropriate category
		for (String name : sortedNames) {
			if (name.startsWith("filter_")) {
				filters.add(name);
			}
			else if (name.startsWith("map_")) {
				mappers.add(name);
			}
			else if (name.startsWith("split_")) {
				splitters.add(name);
			}
		}

		// Session Types
		sessionTypes = new Vector<String>();
		sessionTypes.add("FC_CDSession");
		sessionTypes.add("HoustonHumanCDSession");
		sessionTypes.add("HumanCDSession");
		sessionTypes.add("HumanListMemorySession");
		sessionTypes.add("HumanSDSession");
		sessionTypes.add("MTSOFSSession");
		sessionTypes.add("MovementSession");
		sessionTypes.add("N_ItemMTSSession");
		sessionTypes.add("OFSSession");
		sessionTypes.add("PseudoSDSession");
		sessionTypes.add("SDSession");
		sessionTypes.add("StroopSession");
		sessionTypes.add("YN_CDSession");
	}

	@Override
	public void run()
	{
		analysis.analyze();
	}

	public void REFL_map_cp_status()
	{
		analysis.addMap(new CorrectionProcedureStatusMap());
	}

	public void REFL_map_count_correct()
	{
		analysis.addMap(new CountCorrectMap());
	}

	public void REFL_map_count()
	{
		analysis.addMap(new CountMap());
	}

	public void REFL_map_incorrect_corrections()
	{
		analysis.addMap(new IncorrectCorrectionsMap());
	}

	public void REFL_map_mean_actual_view_time()
	{
		analysis.addMap(new MeanActualViewTimeMap());
	}

	public void REFL_map_mean_response_time()
	{
		analysis.addMap(new MeanResponseTimeMap());
	}

	public void REFL_map_mean_fr_response_time()
	{
		analysis.addMap(new MeanSampleCompletionResponseTimeMap());
	}

	public void REFL_map_mean_fr()
	{
		analysis.addMap(new MeanSampleResponseMap());
	}

	public void REFL_map_median_response_time()
	{
		analysis.addMap(new MedianResponseTimeMap());
	}

	public void REFL_map_percent_correct()
	{
		analysis.addMap(new PercentCorrectMap());
	}

	public void REFL_map_session_information()
	{
		analysis.addMap(new SessionInformationMap());
	}

	// ----------- END MAPPERS ---------------//

	// ----------- BEGIN FILTERS ---------------//
	// ----------- END FILTERS ---------------//

	// ----------- BEGIN SPLITTERS ---------------//

	public void REFL_split_baseline_transfer()
	{
		analysis.addSplitter(new BaselineTransferSplitter());
	}

	// public void REFL_split_change_item_mahalanobis()
	// {
	// analysis.addSplitter(new ChangeItemMahalanobisSplitter());
	// }

	public void REFL_split_choice_set_size()
	{
		analysis.addSplitter(new ChoiceSetSizeSplitter());
	}

	public void REFL_split_configuration()
	{
		analysis.addSplitter(new ConfigurationSplitter());
	}

	public void REFL_split_correct_incorrect()
	{
		analysis.addSplitter(new CorrectIncorrectSplitter());
	}

	public void REFL_split_correct_position()
	{
		analysis.addSplitter(new CorrectPositionSplitter());
	}

	public void REFL_split_item_configuration()
	{
		analysis.addSplitter(new ItemConfigurationSplitter());
	}

	public void REFL_split_probe_delay()
	{
		analysis.addSplitter(new ProbeDelaySplitter());
	}

	public void REFL_split_pseudo_true()
	{
		analysis.addSplitter(new PseudoTrueSplitter());
	}

	public void REFL_split_sample_color()
	{
		analysis.addSplitter(new SampleColorSplitter());
	}

	public void REFL_split_sample_set_size()
	{
		analysis.addSplitter(new SampleSetSizeSplitter());
	}

	public void REFL_split_trial_type()
	{
		analysis.addSplitter(new TrialTypeSplitter(true));
	}

	// ----------- END SPLITTERS ---------------//

	// ----------- BEING FILTERS ---------------//

	public void REFL_filter_response_time_10s()
	{
		analysis.addFilter(new ResponseTimeFilter(new ComparisonRule(ComparisonRule.LT_OR_EQ,
				10 * 1000)));
	}

	public void REFL_filter_correct_trials_only()
	{
		analysis.addFilter(new CorrectTrialsOnlyFilter());
	}

	public void REFL_filter_fr_response_time_4sd()
	{
		analysis.addFilter(new SampleResponseResponseTimeFilter(
				SampleResponseResponseTimeFilter.FOUR_SIGMA));
	}

	public void REFL_filter_cp_on()
	{
		analysis.addFilter(new CorrectionProcedureFilter(CorrectionProcedureFilter.CP_ON));
	}

	public void REFL_filter_cp_off()
	{
		analysis.addFilter(new CorrectionProcedureFilter(CorrectionProcedureFilter.CP_OFF));
	}

	// ----------- END Filters---------------//

	public void loadData(File dboFile, Session session) throws IOException, SQLException,
			SAXException, ParserConfigurationException
	{
		analysis = new Analysis(SessionFactory.BuildSessions(session, dboFile));

	}

	public void LoadData(String directory, String filename, Session session) throws IOException,
			SQLException, SAXException, ParserConfigurationException
	{
		FileTypeConverter.CreateZipFileFromDirectory(directory, filename, session);
		File zipFile = new File(directory + filename + ".dbo");

		analysis = new Analysis(SessionFactory.BuildSessions(session, zipFile));
	}

	public void AddStep(String command)
	{
		try {
			// System.out.println("Trying: " + command);
			methods.get("REFL_" + command).invoke(this);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadData(File selectedFile, String selectedItem) throws IOException, SQLException,
			SAXException, ParserConfigurationException, InstantiationException,
			IllegalAccessException, ClassNotFoundException
	{
		loadData(selectedFile, (Session) Class.forName("sessions." + selectedItem).newInstance());
	}

}

package core;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

class RunAction implements ActionListener
{
	AnalysisBuilderGUI	abgui;

	RunAction(AnalysisBuilderGUI gui)
	{
		abgui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		abgui.loadFile();

		abgui.ab.analysis.clearFilters();
		abgui.ab.analysis.clearMaps();
		abgui.ab.analysis.clearSplitters();

		for (JCheckBox jcb : abgui.ckMaps) {
			if (jcb.isSelected()) {
				abgui.ab.AddStep(jcb.getText().toLowerCase().replaceAll(" ", "_"));
			}
		}
		for (JCheckBox jcb : abgui.ckSplits) {
			if (jcb.isSelected()) {
				abgui.ab.AddStep(jcb.getText().toLowerCase().replaceAll(" ", "_"));
			}
		}
		for (JCheckBox jcb : abgui.ckFilters) {
			if (jcb.isSelected()) {
				abgui.ab.AddStep(jcb.getText().toLowerCase().replaceAll(" ", "_"));
			}
		}
		abgui.run();
	}
}

class DBOCreater implements ActionListener
{

	AnalysisBuilderGUI	abgui;

	public DBOCreater(AnalysisBuilderGUI abg)
	{
		abgui = abg;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		JFileChooser jfc = new JFileChooser();
		int res = jfc.showOpenDialog(abgui);
		if (res == JFileChooser.APPROVE_OPTION) {
			File f = jfc.getSelectedFile();
			abgui.creatDBO(f);
		}
	}

}

class FileLauncher implements ActionListener
{
	AnalysisBuilderGUI	ab;

	public FileLauncher(AnalysisBuilderGUI abgui)
	{
		ab = abgui;
	}

	public void actionPerformed(ActionEvent arg0)
	{
		JFileChooser jfc = new JFileChooser();
		int res = jfc.showOpenDialog(ab);
		if (res == JFileChooser.APPROVE_OPTION) {
			ab.fileToLoad = jfc.getSelectedFile();
		}

		ab.setAllVisible(true);
	}
}

public class AnalysisBuilderGUI extends JFrame
{
	private static final long	serialVersionUID	= 1L;
	private JPanel				maps, filters, splits, north;
	public Vector<JCheckBox>	ckMaps, ckSplits, ckFilters;
	private JComboBox			sessionNames;
	private JButton				launchFileDialog, launchCreateDBO;
	public File					fileToLoad;
	public AnalysisBuilder		ab;

	public AnalysisBuilderGUI()
	{
		super("Katalabaino 1.1");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(200, 200, 800, 500);

		Container con = getContentPane();
		con.setLayout(new BorderLayout());

		ab = new AnalysisBuilder();

		sessionNames = new JComboBox(ab.sessionTypes);

		launchFileDialog = new JButton("Load DBO");
		launchFileDialog.addActionListener(new FileLauncher(this));

		launchCreateDBO = new JButton("Create DBO");
		launchCreateDBO.addActionListener(new DBOCreater(this));
		north = new JPanel();
		north.setLayout(new FlowLayout());

		north.add(sessionNames);
		north.add(launchCreateDBO);
		north.add(launchFileDialog);

		con.add(north, BorderLayout.NORTH);
		addOptions();
		setAllVisible(false);
	}

	public void creatDBO(File f)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("hh_mm_a_dd-MMM-yy");
		String name = f.getName().split("[^A-Za-z]")[0] + sdf.format(new Date()) + ".dbo";

		try {
			Session s = (Session) Class.forName(
					"sessions." + (String) sessionNames.getSelectedItem()).newInstance();
			fileToLoad = FileTypeConverter.CreateZipFileFromDirectory(f.getParent() + "/", name, s);

			setAllVisible(true);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run()
	{
		// Thread t = new Thread(ab);
		// t.start();

		ab.run();
	}

	public void setAllVisible(boolean visible)
	{
		maps.setVisible(visible);
		filters.setVisible(visible);
		splits.setVisible(visible);
	}

	public void loadFile()
	{
		try {
			ab.loadData(fileToLoad, (String) sessionNames.getSelectedItem());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addOptions()
	{
		Container con = getContentPane();

		maps = new JPanel(new GridLayout(0, 1));
		ckMaps = new Vector<JCheckBox>();
		for (String map : AnalysisBuilder.mappers) {
			JCheckBox jcb = new JCheckBox(map.replaceAll("_", " "));
			ckMaps.add(jcb);
			maps.add(jcb);
		}

		splits = new JPanel(new GridLayout(0, 1));
		ckSplits = new Vector<JCheckBox>();
		for (String split : AnalysisBuilder.splitters) {
			JCheckBox jcb = new JCheckBox(split.replaceAll("_", " "));
			ckSplits.add(jcb);
			splits.add(jcb);
		}

		filters = new JPanel(new GridLayout(0, 1));
		ckFilters = new Vector<JCheckBox>();
		for (String filter : AnalysisBuilder.filters) {
			JCheckBox jcb = new JCheckBox(filter.replaceAll("_", " "));
			ckFilters.add(jcb);
			filters.add(jcb);
		}

		// System.out.println("Splits: " + ckSplits.size());
		// System.out.println("Maps: " + ckMaps.size());
		// System.out.println("Filters: " + ckFilters.size());

		con.add(splits, BorderLayout.WEST);
		con.add(maps, BorderLayout.CENTER);
		con.add(filters, BorderLayout.EAST);
		JButton runIt = new JButton("Run!");
		runIt.addActionListener(new RunAction(this));
		con.add(runIt, BorderLayout.SOUTH);

		this.validate();
	}

}

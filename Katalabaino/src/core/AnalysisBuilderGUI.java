package core;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

class HideAction implements ActionListener, FocusListener
{
	protected JFrame	jf;

	HideAction(JFrame jf)
	{
		this.jf = jf;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		jf.setVisible(false);
	}

	@Override
	public void focusGained(FocusEvent arg0)
	{}

	@Override
	public void focusLost(FocusEvent arg0)
	{
		jf.setVisible(false);
	}

}

class ShowAction implements ActionListener
{
	protected JFrame	jf;

	ShowAction(JFrame jf)
	{
		this.jf = jf;
	}

	public void actionPerformed(ActionEvent arg0)
	{
		jf.setVisible(true);
	}
}

class RandomPictureAction implements ActionListener
{
	AnalysisBuilderGUI	ab;
	Random				rand;

	public RandomPictureAction(AnalysisBuilderGUI ab)
	{
		this.ab = ab;
		rand = new Random();
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		try {
			BufferedImage myPicture;

			int pic = rand.nextInt(ab.images.length);

			myPicture = ImageIO.read(new File("fp/" + ab.images[pic]));
			Rectangle r = ab.jw.getBounds();
			ab.picLabel.setIcon(new ImageIcon(myPicture));
			r.height = myPicture.getHeight() + 5;
			r.width = myPicture.getWidth() + 5;

			r.x = ab.getBounds().x + ab.getBounds().width;
			r.y = ab.getBounds().y;

			ab.jw.setBounds(r);
			ab.jw.invalidate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class ClearCheckAction implements ActionListener
{
	private Vector<JCheckBox>[]	boxes;

	public ClearCheckAction(Vector<JCheckBox>... jcb)
	{
		boxes = jcb;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		for (Vector<JCheckBox> boxVector : boxes)
			for (JCheckBox box : boxVector)
				box.setSelected(false);
	}

}

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

		boolean thereIsWorkToDo = false;

		abgui.loadFile();

		abgui.ab.analysis.clearFilters();
		abgui.ab.analysis.clearMaps();
		abgui.ab.analysis.clearSplitters();

		for (JCheckBox jcb : abgui.ckMaps) {
			if (jcb.isSelected()) {
				abgui.ab.AddStep("map_" + jcb.getText().toLowerCase().replaceAll(" ", "_"));
				thereIsWorkToDo = true;
			}
		}
		for (JCheckBox jcb : abgui.ckSplits) {
			if (jcb.isSelected()) {
				abgui.ab.AddStep("split_" + jcb.getText().toLowerCase().replaceAll(" ", "_"));
			}
		}
		for (JCheckBox jcb : abgui.ckFilters) {
			if (jcb.isSelected()) {
				abgui.ab.AddStep("filter_" + jcb.getText().toLowerCase().replaceAll(" ", "_"));
			}
		}
		if (thereIsWorkToDo) abgui.run();
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
		abgui.jfc.setFileFilter(new FileFilter() {

			@Override
			public String getDescription()
			{
				return "Katalabaino Compatible";
			}

			@Override
			public boolean accept(File arg0)
			{
				return arg0.isDirectory() || arg0.getName().endsWith(".tr")
						|| arg0.getName().endsWith(".mdb");
			}
		});

		int res = abgui.jfc.showOpenDialog(abgui);
		if (res == JFileChooser.APPROVE_OPTION) {
			File f = abgui.jfc.getSelectedFile();
			abgui.createDBO(f);
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

		ab.jfc.setFileFilter(new FileFilter() {

			@Override
			public String getDescription()
			{
				return "Katalabaino Compatible";
			}

			@Override
			public boolean accept(File arg0)
			{
				return arg0.isDirectory() || arg0.getName().endsWith("dbo");
			}
		});

		int res = ab.jfc.showOpenDialog(ab);
		if (res == JFileChooser.APPROVE_OPTION) {
			ab.fileToLoad = ab.jfc.getSelectedFile();
			ab.setAllVisible(true);
		}
	}
}

public class AnalysisBuilderGUI extends JFrame
{
	private static final long	serialVersionUID	= 1L;
	private JPanel				maps, filters, splits, north;
	public Vector<JCheckBox>	ckMaps, ckSplits, ckFilters;
	private JComboBox			sessionNames;
	private JButton				launchFileDialog, launchCreateDBO, clearCheckBoxes;
	public File					fileToLoad;
	public AnalysisBuilder		ab;
	public JFrame				jw;
	public JFileChooser			jfc;
	public JLabel				fileLabel, picLabel;

	public String[]				images;

	public AnalysisBuilderGUI()
	{
		super("Katalabaino 1.1");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(200, 200, 550, 500);

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

		try {
			images = new File("fp/").list(new FilenameFilter() {

				@Override
				public boolean accept(File arg0, String arg1)
				{
					return arg1.endsWith("jpg");
				}
			});

			// System.out.println("len: " + images.length);

			picLabel = new JLabel();

			jw = new JFrame("Done!");
			jw.getContentPane().setLayout(new BorderLayout());
			jw.getContentPane().add(picLabel, BorderLayout.CENTER);
			jw.setBounds(getBounds().x + getBounds().width, getBounds().y, 300, 334);
			jw.addFocusListener(new HideAction(jw));
			jw.setAlwaysOnTop(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// initialize the JFC to a nice place. look for the usual suspects, then default windows
		// location, then a more specific mac location
		String[] places = { "Y:/warehouse/", "C:/warehouse/", "C:/information/", "C:/", "D:/",
				"/Users/jmagnotti/warehouse/" };
		File f = null;
		int i = 0;
		do {
			f = new File(places[i]);
			i++;
		}
		while (i < places.length && !f.exists());

		if (!f.exists()) f = null;

		jfc = new JFileChooser(f);
	}

	public void createDBO(File f)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("hh_mm_a_dd-MMM-yy");
		String name = f.getName().split("[^A-Za-z]")[0] + "_" + sdf.format(new Date()) + ".dbo";

		try {
			Session s = (Session) Class.forName(
					"sessions." + (String) sessionNames.getSelectedItem()).newInstance();
			fileToLoad = FileTypeConverter.CreateZipFileFromDirectory(f.getParent() + "/", name, s);

			setAllVisible(true);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run()
	{
		// Thread t = new Thread(ab);
		// t.start();
		new HideAction(jw).actionPerformed(null);
		jw.invalidate();
		ab.run();
		new RandomPictureAction(this).actionPerformed(null);
		new ShowAction(jw).actionPerformed(null);
	}

	public void setAllVisible(boolean visible)
	{
		maps.setVisible(visible);
		filters.setVisible(visible);
		splits.setVisible(visible);
		clearCheckBoxes.setVisible(visible);
		if (fileToLoad != null)
			fileLabel.setText("<html>Using: <b>" + fileToLoad.getName() + "</b></html>");
	}

	public void loadFile()
	{
		try {
			ab.loadData(fileToLoad, (String) sessionNames.getSelectedItem());
			setAllVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addOptions()
	{
		Container con = getContentPane();

		JCheckBox temp = new JCheckBox();
		Font font = temp.getFont();
		font = new Font(font.getName(), font.getStyle(), font.getSize() + 6);

		JLabel description;

		maps = new JPanel(new GridLayout(15, 1));
		ckMaps = new Vector<JCheckBox>();

		description = new JLabel("<html><b><u>Output</u></b></html>");
		description.setFont(font);

		maps.add(description);

		for (String map : AnalysisBuilder.mappers) {
			JCheckBox jcb = new JCheckBox(ucwords(map.replaceAll("map_", "").replaceAll("_", " ")));
			// jcb.setFont(font);

			ckMaps.add(jcb);
			maps.add(jcb);
		}

		splits = new JPanel(new GridLayout(15, 1));
		ckSplits = new Vector<JCheckBox>();

		description = new JLabel("<html><b><u>Group By</u></b></html>");
		description.setFont(font);

		splits.add(description);

		for (String split : AnalysisBuilder.splitters) {
			JCheckBox jcb = new JCheckBox(ucwords(split.replaceAll("split_", "").replaceAll("_",
					" ")));
			// jcb.setFont(font);
			ckSplits.add(jcb);
			splits.add(jcb);
		}

		filters = new JPanel(new GridLayout(15, 1));
		ckFilters = new Vector<JCheckBox>();

		description = new JLabel("<html><b><u>Filter By</u></b></html>");
		description.setFont(font);

		filters.add(description);

		for (String filter : AnalysisBuilder.filters) {
			JCheckBox jcb = new JCheckBox(ucwords(filter.replaceAll("filter_", "").replaceAll("_",
					" ")));
			// jcb.setFont(font);
			ckFilters.add(jcb);
			filters.add(jcb);
		}

		// System.out.println("Splits: " + ckSplits.size());
		// System.out.println("Maps: " + ckMaps.size());
		// System.out.println("Filters: " + ckFilters.size());

		JPanel cbPanel = new JPanel(new GridLayout(1, 3));

		cbPanel.add(splits);
		cbPanel.add(maps);
		cbPanel.add(filters);
		con.add(cbPanel, BorderLayout.CENTER);

		JPanel south = new JPanel(new GridLayout(1, 3));
		fileLabel = new JLabel("No file loaded.");

		JButton runIt = new JButton("Run!");
		runIt.addActionListener(new RunAction(this));

		south.add(fileLabel);
		south.add(runIt);

		con.add(south, BorderLayout.SOUTH);

		clearCheckBoxes = new JButton("Clear Options");
		clearCheckBoxes.addActionListener(new ClearCheckAction(ckMaps, ckSplits, ckFilters));
		north.add(clearCheckBoxes);

		this.validate();
	}

	private String ucwords(String str)
	{
		String res = "";
		String[] pieces = str.split(" ");

		for (int i = 0; i < pieces.length; i++) {
			res += Character.toUpperCase(pieces[i].charAt(0));

			if (pieces[i].length() < 3)
				res += Character.toUpperCase(pieces[i].charAt(1));
			else
				res += pieces[i].substring(1);

			if (i < pieces.length - 1) res += " ";
		}

		return res;
	}

}

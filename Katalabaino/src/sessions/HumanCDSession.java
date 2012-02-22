package sessions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import core.Session;

public class HumanCDSession extends Session
{
	public HumanCDSession()
	{
		super();
	}

	public HumanCDSession(File xmlFile) throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException
	{
		this(new FileInputStream(xmlFile));
	}

	public HumanCDSession(InputStream stream) throws ParserConfigurationException, SAXException,
			IOException
	{
		super(stream);
	}

	@Override
	public Session fromMDB(Connection session, Connection results, String sName, String rName)
			throws SQLException
	{
		System.err.println("Not Implemented with good reason. MDB files are for the birds!");
		return null;
	}

	@Override
	public Session fromXML(File xmlFile) throws FileNotFoundException, SAXException, IOException,
			ParserConfigurationException
	{
		return new HumanCDSession(xmlFile);
	}

	@Override
	public Session fromStream(InputStream stream) throws FileNotFoundException, SAXException,
			IOException, ParserConfigurationException
	{
		return new HumanCDSession(stream);
	}

}

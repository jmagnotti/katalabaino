package analyses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import core.session.Session;

public class CountingStroopSession extends Session
{

	public CountingStroopSession()
	{
		super();
	}

	public CountingStroopSession(InputStream is) throws ParserConfigurationException, SAXException,
			IOException
	{
		super(is);
	}

	@Override
	public Session fromMDB(Connection session, Connection results, String sName, String rName)
			throws SQLException
	{
		System.err.println("CoutingStroopSession.java says: this is going to work...");

		return null;
	}

	@Override
	public Session fromXML(File xmlFile) throws FileNotFoundException, SAXException, IOException,
			ParserConfigurationException
	{
		return fromStream(new FileInputStream(xmlFile));
	}

	@Override
	public Session fromStream(InputStream stream) throws FileNotFoundException, SAXException,
			IOException, ParserConfigurationException
	{
		return new CountingStroopSession(stream);
	}

}

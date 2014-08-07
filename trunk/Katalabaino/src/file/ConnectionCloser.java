package file;

import java.sql.Connection;

public class ConnectionCloser implements Runnable
{
	Connection	c;

	public ConnectionCloser(Connection con)
	{
		c = con;
	}

	public void run()
	{
		try {
			c.close();
		}
		catch (Exception e) {

		}
	}

	public static void shutIt(Connection con)
	{
		new Thread(new ConnectionCloser(con)).start();
	}
}

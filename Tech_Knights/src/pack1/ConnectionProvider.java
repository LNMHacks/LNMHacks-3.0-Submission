package pack1;
import java.sql.Connection;
import java.sql.DriverManager;
public class ConnectionProvider 
{
	public static Connection getCon()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
			return con;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
}

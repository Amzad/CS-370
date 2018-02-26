import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MYSql {

	protected static String DB_Driver;
	protected static String DB_Server;

	protected static String DBusername;
	protected static String DBpassword;

	Connection connection = null;
	Statement command = null;

	public MYSql() {
		DB_Driver = "com.mysql.jdbc.Driver";

		test();
		connect();

	}

	public MYSql(boolean privilleges, String username, String password) {
		DB_Driver = "com.mysql.jdbc.Driver";

		if (privilleges == true) {

		} else {
			DBusername = "username";
			DBpassword = "password";
		}

		DB_Server = "jdbc:mysql//localhost/CS370";

	}

	public void setHomeServer() {
		DB_Server = "jdbc:mysql//projectbirb.me:3306/cs370";
	}

	public void setLocalServer() {
		DB_Server = "jdbc:mysql//localhost/CS370";
	}

	public String createDB() {
		String status = "false";

		return status;
	}

	public boolean Insert() {

		return false;
	}

	public boolean Delete() {

		return false;
	}

	public String Select() {
		String data = "";
		return data;
	}

	public boolean closeConnection() {

		return false;
	}

	public void test() {
		DBusername = "Amzad";
		DBpassword = "blah1234";
		DB_Server = "jdbc:mysql://192.168.1.16:3306/cs370?useSSL=false";
	}

	public boolean connect() {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			System.out.println("Trying DB Connection");
			connection = DriverManager.getConnection(DB_Server, DBusername, DBpassword);
			System.out.print("Connection Established");
			return true;

		} catch (Exception e) {
			System.out.println(e);
			return false;
		} finally {

		}
	}

}

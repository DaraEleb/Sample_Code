package database;

import java.sql.*;

public class SQLiteJDBC {
	Connection conn;
	Statement stmt;
	
	
	public SQLiteJDBC() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:pass.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		System.out.println("Opened database successfully");
	}

	public void createTable() {
		Statement stmnt;

		try {
			stmnt = conn.createStatement();
			String create = "CREATE TABLE if not exists auth(username string, password string);";
			stmnt.executeUpdate(create);
			stmnt.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		System.out.println("Table created successfully");
	}

	public void insert(String user, String pass) throws SQLException {
		Statement stmnt;
		stmnt = conn.createStatement();
		String query = "INSERT INTO auth VALUES ('" + user + "', '" + pass
				+ "');";
		stmnt.executeUpdate(query);
		stmnt.close();
		System.out.println("user+pass inserted successfully");
	}

	public ResultSet select(String user, String pass) throws SQLException {
		Statement stmnt;
		String query = "";
		ResultSet result = null;

		stmnt = conn.createStatement();

		if ((user != null) && (pass != null)) {
			query = "SELECT * FROM auth WHERE username LIKE '" + user
					+ "' AND password LIKE '" + pass + "';";
			result = stmnt.executeQuery(query);
		} else if (user == null) {
			query = "SELECT * FROM auth WHERE password LIKE '" + pass + "';";
			result = stmnt.executeQuery(query);
		} else if (pass == null) {
			query = "SELECT * FROM auth WHERE username LIKE '" + user + "';";
			result = stmnt.executeQuery(query);
		}

		return result;
	}

	public ResultSet selectAll() throws SQLException {
		Statement stmnt;
		String query = "";
		ResultSet result = null;

		stmnt = conn.createStatement();
		query = "select * from auth";
		result = stmnt.executeQuery(query);

		return result;
	}
	
	public ResultSet executeQuery(String query) throws SQLException{
		stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery(query);
		return res;
	}
	
	public int executeUpdate(String update) throws SQLException{
		stmt = conn.createStatement();
		int res = stmt.executeUpdate(update);
		return res;
	}

	public void close() throws SQLException {
		conn.close();
	}

	/**
	 * checks if user and pass provided is in the database. user is not case
	 * sensitive while password is.
	 * 
	 * @param user
	 * @param pass
	 * @return
	 */
	public boolean validateUser(String user, String pass) {
		try {
			String query = "select exists(select 1 from auth where username LIKE '"
					+ user + "' and password='" + pass + "')";
			System.out.println(query);
			ResultSet rs = executeQuery(query);
			rs.next();
			int result = rs.getInt(1);
			stmt.close();
			System.out.println("result " + result );
			if (result == 1)
				return true;

		} catch (SQLException e) {
			return false;
			//e.printStackTrace();
		}

		return false;
	}

	public boolean saveImage(String username, String imageName) {
		
		
		

		return false;
	}

	public boolean uploadImage(String username, String imageName) {

		return false;
	}
	
	public boolean deleteImage(String username, String imageName) {

		return false;
	}

	public boolean register(String user	, String pass) {
		String query = "insert into auth values ('" + user+ "','"+ pass+ "')";
		// insert it
		try {
			executeUpdate(query);
			stmt.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		
		// validate the insertion
		return validateUser(user, pass);
		
	}


}

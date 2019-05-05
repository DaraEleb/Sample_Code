package database;

import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JDBCTest {
	public static void main(String[] args) throws IOException {
		SQLiteJDBC sqliteDB = new SQLiteJDBC();
		sqliteDB.createTable();
		ResultSet rs1;
		try {
			sqliteDB.insert("testuser", "testpass");
			rs1 = sqliteDB.select("testuser", null);

			ResultSet rs2 = sqliteDB.select(null, "testpass");

			while (rs1.next()) {
				System.out.println(rs1.getString(1) + " " + rs1.getString(2));
			}

			while (rs2.next()) {
				System.out.println(rs2.getString(1) + " " + rs2.getString(2));
			}

			System.out.println("WTF IS GOING ON  HERE!!!!");
			
			String user, pass;
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			System.out.println("username:");
			user = "aba";//bufferRead.readLine();

			System.out.println("password:");
			pass = "aba";//bufferRead.readLine();

			sqliteDB.insert(user, pass);
//			ResultSet rs3 = sqliteDB.select(user, null);
//			ResultSet rs4 = sqliteDB.select(null, pass);
//
//			while (rs3.next()) {
//				System.out.println(rs3.getString(1) + " " + rs3.getString(2));
//			}
//
//			while (rs4.next()) {
//				System.out.println(rs4.getString(1) + " " + rs4.getString(2));
//			}

			ResultSet rs11 = sqliteDB.selectAll();

			System.out.println("Printing out all stuff!!");
			while (rs11.next()) {
				System.out.println(rs11.getString(1) + " " + rs11.getString(2));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}finally{
			try {
				sqliteDB.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
}

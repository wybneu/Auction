package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;

public class UserDB {
	private static final long serialVersionUID = 1L;
	private String framework = "derbyclient";
	private String driver = "org.apache.derby.jdbc.ClientDriver";
	private String protocol = "jdbc:derby://localhost:1527/";
	Connection conn = null;
	Properties props = new Properties();
	Statement s = null;
	ResultSet rs = null;
	static String dbName = "auction";
	static String tableName = "users";
	PreparedStatement psInsert = null;
	PreparedStatement psDelete = null;
	
	public UserDB(){
		
	}
	public void add(String name, String password) throws SQLException {

		try {
			if (name.equals(null))
				return;
			psInsert.setString(1, name);
			psInsert.setString(2, password);
			psInsert.executeUpdate();
			return;
		} catch (NumberFormatException e) {
			System.out.println("Wrong input type, please input again");
		}
	}
	
	private void connectTable() throws SQLException {
		conn.setAutoCommit(false);
		DatabaseMetaData meta = conn.getMetaData();
		ResultSet res = meta.getTables(null, null, null,
				new String[] { "TABLE" });
		HashSet<String> set = new HashSet<String>();
		while (res.next()) {
			set.add(res.getString("TABLE_NAME"));
		}
		if (set.contains("users".toUpperCase())) {
			conn.commit();
			s = conn.createStatement();
			s.execute("drop table " + tableName);
			conn.commit();

			s = conn.createStatement();
			s.execute("create table "
					+ tableName
					+ " (NAME varchar(20) NOT NULL UNIQUE, PASSWORD varchar(20) NOT NULL)");
			setTools();
			add("admin", "123456");

		} else {
			s = conn.createStatement();
			s.execute("create table "
					+ tableName
					+ " (NAME varchar(20) NOT NULL UNIQUE, PASSWORD varchar(20) NOT NULL)");
			setTools();
			add("admin", "123456");
		}
		conn.commit();
	}
	private void setTools() throws SQLException {
		psInsert = conn.prepareStatement("insert into " + tableName
				+ " values (?, ?)");
		psDelete = conn.prepareStatement("DELETE FROM " + tableName
				+ " WHERE NAME = ?");
	}
	private int searchName(String name, String password) throws SQLException {
		conn.commit();
		s = conn.createStatement();
		rs = s.executeQuery("select NAME,PASSWORD from " + tableName
				+ " ORDER BY NAME");
		if (!rs.next()) {
			return 3; // database empty
		}

		do {
			if (rs.getString("NAME").equalsIgnoreCase(name))
				if (rs.getString("PASSWORD").equals(password)) 
					return 1;
				else
					return 2;
		} while (rs.next());
		return 0;
	}
	
}

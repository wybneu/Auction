
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;
import org.json.*;

import java.util.Collection;

/**
 * Servlet implementation class myurls
 */
@WebServlet("/myurls")
public class myurls extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String framework = "derbyclient";
	private String driver = "org.apache.derby.jdbc.ClientDriver";
	private String protocol = "jdbc:derby://localhost:1527/";

	String mode = "embedded";
	Connection conn = null;
	Properties props = new Properties();
	ArrayList<Statement> statements = new ArrayList<Statement>(); // list of
																	// Statements,
	PreparedStatement psInsert = null;
	PreparedStatement psDelete = null;
	Statement s = null;
	ResultSet rs = null;
	static String dbName = "URLCollection";
	static String tableName = "URLs";
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public myurls() {
		super();
		

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	private void display() throws SQLException {
		System.out.println("              Table " + tableName);

		try {
			s = conn.createStatement();
			rs = s.executeQuery("select ID,URL,URLDATE,URLTIME from "
					+ tableName + " ORDER BY ID");
			if (!rs.next()) {
				System.out.println("There is no data in this table");
				return;
			}
			System.out.println("ID\tURLDate\t\tURLTime\t\tURL");
			do {
				StringBuilder sb = new StringBuilder(rs.getInt("ID"));
				sb.append(rs.getInt("ID"));
				sb.append("\t");
				sb.append(rs.getDate("URLDate"));
				sb.append("\t");
				sb.append(rs.getTime("URLTime"));
				sb.append("\t");
				sb.append(rs.getString("URL"));
				System.out.println(sb.toString());
			} while (rs.next());
		} catch (NullPointerException e) {
			System.out.println("NULL");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		JSONArray list = new JSONArray();
		try {
			Class.forName(driver).newInstance();
		} catch (ClassNotFoundException cnfe) {
			System.err.println("\nUnable to load the JDBC driver " + driver);
			System.err.println("Please check your CLASSPATH.");
			cnfe.printStackTrace(System.err);
		} catch (InstantiationException ie) {
			System.err.println("\nUnable to instantiate the JDBC driver "
					+ driver);
			ie.printStackTrace(System.err);
		} catch (IllegalAccessException iae) {
			System.err.println("\nNot allowed to access the JDBC driver "
					+ driver);
			iae.printStackTrace(System.err);
		} catch (Exception e) {
			System.out.print("Unkown error");
		}
		try {
			setProperties();
			createDB();
			createTable();
			setTools();
			add(1,"www.google.com");
			add(2,"www.youtube.com");
			add(3,"www.unimelb.com");
			add(4,"lms.unimelb.com");
			add(5,"www.facebook.com");
			conn.commit();
			display();
			s = conn.createStatement();

			rs = s.executeQuery("select ID,URL,URLDATE,URLTIME from "
					+ tableName + " ORDER BY ID");
			if (!rs.next()) {
				out.write("NUll");
				return;
			}

			do {
				list.put(rs.getString("URL"));
//				 obj.put("ID", rs.getInt("ID"));
//				 JSONArray list = new JSONArray();
//				 JSONObject URLDate = new JSONObject();
//				 URLDate.put("URLDate", rs.getDate("URLDate").toString());
//				 JSONObject URLTime = new JSONObject();
//				 URLTime.put("URLTime", rs.getTime("URLTime").toString());
//				 JSONObject URL = new JSONObject();
//				 URL.put("URL", rs.getString("URL"));
//				 list.put(URLDate);
//				 list.put(URLTime);
//				 list.put(URL);
//				 obj.put("url", list);
//				StringBuilder sb = new StringBuilder(rs.getInt("ID"));
//				sb.append(rs.getInt("ID"));
//				sb.append("\t");
//				sb.append(rs.getDate("URLDate"));
//				sb.append("\t");
//				sb.append(rs.getTime("URLTime"));
//				sb.append("\t");
//				sb.append(rs.getString("URL"));
//				out.print(sb.toString());
			} while (rs.next());
			 obj.put("urls", list);
			 out.print(obj);
			 conn.commit();
		
		} catch (SQLException sqle) {
			printSQLException(sqle);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (SQLException sqle) {
				printSQLException(sqle);
			}
			int i = 0;
			while (!statements.isEmpty()) {
				Statement st = (Statement) statements.remove(i);
				try {
					if (st != null) {
						st.close();
						st = null;
					}
				} catch (SQLException sqle) {
					printSQLException(sqle);
				}
			}
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException sqle) {
				printSQLException(sqle);
			}
		}
	}

	public static void printSQLException(SQLException e) {
		while (e != null) {
			System.err.println("     failed");
			System.err.println("----- SQLException -----");
			System.err.println("  SQL State:  " + e.getSQLState());
			System.err.println("  Error Code: " + e.getErrorCode());
			System.err.println("  Message:    " + e.getMessage());
			e = e.getNextException();
		}
	}
	private void setTools() throws SQLException {
		psInsert = conn.prepareStatement("insert into " + tableName
				+ " values (?, ?, ?, ?)");
		psDelete = conn.prepareStatement("DELETE FROM " + tableName
				+ " WHERE ID = ?");
	}
	private void setProperties() {
		props.put("user", "admin");
		props.put("password", "123456");
	}

	private void createDB() throws SQLException {
		System.out.print("Connect to the Database " + dbName);
		conn = DriverManager.getConnection(protocol + dbName + ";create=true",
				props);
		System.out.println("    finished");

	}
	
	private void add(int id, String url) throws SQLException {

		try {
			if (id == 0)
				return;
			psInsert.setInt(1, id);
			psInsert.setString(2, url);
			psInsert.setDate(3, new Date(System.currentTimeMillis()));
			psInsert.setTime(4, new Time(System.currentTimeMillis()));
			psInsert.executeUpdate();
			System.out.println("Inserted " + id + " " + url);
			return;
		} catch (NumberFormatException e) {
			System.out.println("Wrong input type, please input again");
		}
		
	}
	
	private void createTable() throws SQLException {
		conn.setAutoCommit(false);
		DatabaseMetaData meta = conn.getMetaData();
		ResultSet res = meta.getTables(null, null, null,
				new String[] { "TABLE" });
		HashSet<String> set = new HashSet<String>();
		while (res.next()) {
			set.add(res.getString("TABLE_NAME"));
		}
		if (set.contains("URLs".toUpperCase())) {
			deleteTable();
			s = conn.createStatement();
			statements.add(s);
			s.execute("create table "
					+ tableName
					+ " (ID int NOT NULL UNIQUE, URL varchar(200) NOT NULL, URLDate Date NOT NULL,URLTime Time NOT NULL)");
		} else {
			s = conn.createStatement();
			statements.add(s);
			s.execute("create table "
					+ tableName
					+ " (ID int NOT NULL UNIQUE, URL varchar(200) NOT NULL, URLDate Date NOT NULL,URLTime Time NOT NULL)");
		}
		display();
		conn.commit();

	}
	private void deleteTable() throws SQLException {
		if (psInsert != null) {
			psInsert.close();
			psInsert = null;
		}
		if (psDelete != null) {
			psDelete.close();
			psDelete = null;
		}
		if (rs != null) {
			rs.close();
			rs = null;
		}
		conn.commit();
		s = conn.createStatement();
		s.execute("drop table " + tableName);
		conn.commit();
	}

}

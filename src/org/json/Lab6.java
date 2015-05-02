package org.json;

import java.sql.Connection;
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

public class Lab6 {
	private String framework = "embedded";
	private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private String protocol = "jdbc:derby:";
	Scanner sc = new Scanner(System.in);
	String mode = "embedded";
	Connection conn = null;
	Properties props = new Properties();
	ArrayList<Statement> statements = new ArrayList<Statement>(); // list of
																	// Statements,
	// PreparedStatements
	PreparedStatement psInsert = null;
	PreparedStatement psDelete = null;
	Statement s = null;
	ResultSet rs = null;
	static String dbName = "URLCollection";
	static String tableName = "URLs";

	public static void main(String[] args) {
		new Lab6();
	}

	public Lab6() {
		selectMode();
		loadDriver();
		try {
			setProperties();
			createDB();
			createTable();
			setTools();
			operation();

		} catch (SQLException sqle) {
			printSQLException(sqle);
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

	private void setTools() throws SQLException {
		psInsert = conn.prepareStatement("insert into " + tableName
				+ " values (?, ?, ?, ?)");
		psDelete = conn.prepareStatement("DELETE FROM " + tableName
				+ " WHERE ID = ?");
	}

	private void operation() throws SQLException {
		System.out
				.println("Please choose your operation:\n1 add a new URL"
						+ "\t2 delete a URL\t3 display all\n4 update DB\t5 delete table\t6 Exit this application");
		try {
			int input = sc.nextInt();
			switch (input) {
			case 1:
				add();
				break;
			case 2:
				delete();
				break;
			case 3:
				display();
				break;
			case 4:
				update();
				break;
			case 5:
				deleteTable();
				break;
			case 6:
				closeDB();
				break;
			default:
				System.out.println("input error, please input vaild number");
				break;
			}
		} catch (Exception e) {
			System.out.println("input error, please input vaild number");
			System.out.println(e);
		}
		this.operation();
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
		System.out.print("Dropped table " + tableName);
		s.execute("drop table " + tableName);
		System.out.println("      finished");
		this.closeDB();
		this.printLine();
	}

	private void update() throws SQLException {
		conn.commit();
		System.out.println("Committed the transaction");
		this.printLine();
	}

	private void display() throws SQLException {
		System.out.println("              Table " + tableName);

		try {
			s = conn.createStatement();
			rs = s.executeQuery("select ID,URL,URLDATE,URLTIME from "
					+ tableName + " ORDER BY ID");
			if (!rs.next()) {
				System.out.println("There is no data in this table");
				this.printLine();
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
		this.printLine();

	}

	private void delete() throws SQLException {
		try {
			System.out
					.println("Please input the ID (int) You want to delete, 0 for return");
			int id = sc.nextInt();
			if (id == 0)
				return;
			psDelete.setInt(1, id);
			psDelete.executeUpdate();
			System.out.println("delete " + id);
			this.printLine();
			return;
		} catch (NumberFormatException e) {
			System.out.println("Wrong input type, please input again");
		}
		delete();
	}

	private void add() throws SQLException {

		try {
			System.out
					.println("Please input the ID (int) and URLs (String), 0 for return");

			int id = sc.nextInt();
			if (id == 0)
				return;
			String url = sc.next();

			psInsert.setInt(1, id);
			psInsert.setString(2, url);
			psInsert.setDate(3, new Date(System.currentTimeMillis()));
			psInsert.setTime(4, new Time(System.currentTimeMillis()));
			psInsert.executeUpdate();
			System.out.println("Inserted " + id + " " + url);
			this.printLine();
			return;
		} catch (NumberFormatException e) {
			System.out.println("Wrong input type, please input again");
		}
		add();
	}

	private void closeDB() throws SQLException {
		update();
		System.out.print("Thanks, The dataBase is Exiting     ");
		try {

			if (psInsert != null) {
				psInsert.close();
				psInsert = null;
			}
			if (psDelete != null) {
				psDelete.close();
				psDelete = null;
			}
			if (s != null) {
				s.close();
				s = null;
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
			if (framework.equals("embedded")) {
				try {
					DriverManager.getConnection("jdbc:derby:;shutdown=true");
				} catch (SQLException se) {
					if (((se.getErrorCode() == 50000) && ("XJ015".equals(se
							.getSQLState())))) {
						System.out.println("Derby shut down normally");
					} else {
						System.err.println("Derby did not shut down normally");
						printSQLException(se);
					}
				}
			}
		} catch (Exception e) {
			System.out.print(e);
		}
		System.out.println("finished");
		System.exit(0);

	}

	private void createTable() throws SQLException {
		conn.setAutoCommit(false);
		System.out.print("Open table URL");
		DatabaseMetaData meta = conn.getMetaData();
		ResultSet res = meta.getTables(null, null, null,
				new String[] { "TABLE" });
		HashSet<String> set = new HashSet<String>();
		while (res.next()) {
			set.add(res.getString("TABLE_NAME"));
		}
		if (set.contains("URLs".toUpperCase())) {
			System.out.println("    finished");
			System.out
					.println("The table already exists, you can then explore it");
		} else {
			System.out.println("    failed");
			System.out.println("The table " + tableName
					+ " may not exists, Now the table URLs will be create");
			System.out.print("Table " + tableName + " is creating... ");
			s = conn.createStatement();
			statements.add(s);
			s.execute("create table "
					+ tableName
					+ " (ID int NOT NULL UNIQUE, URL varchar(200) NOT NULL, URLDate Date NOT NULL,URLTime Time NOT NULL)");
			System.out.println("    finished");
			System.out.println("Committed the transaction");
		}
		this.printLine();
		display();
		conn.commit();

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

	private void loadDriver() {
		try {
			System.out.print("Loaded the appropriate driver:" + driver);
			Class.forName(driver).newInstance();
			System.out.println("    finished");
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

	}

	private void selectMode() {
		System.out.println("Welcome to URLCollection Database system");
		System.out
				.println("Please input your mode of derby(number or name of mode)\n1 embedded\n2 network\\derbyclient");
		String choice = sc.next();
		if (choice.equals("1") || choice.equalsIgnoreCase("embedded")) {
			this.setFramework("embedded");
			this.setDriver("org.apache.derby.jdbc.EmbeddedDriver");
			this.setProtocol("jdbc:derby:");
			this.printLine();
			System.out.println("Mode Embedded derby database selected");
			System.out.println("Driver: org.apache.derby.jdbc.EmbeddedDriver");
			System.out.println("protocol: jdbc:derby: ");
			this.printLine();
		} else if (choice.equals("2") || choice.equalsIgnoreCase("network")
				|| choice.equalsIgnoreCase("derbyclient")) {
			this.setFramework("derbyclient");
			this.setDriver("org.apache.derby.jdbc.ClientDriver");
			this.setProtocol("jdbc:derby://localhost:1527/");
			this.printLine();
			System.out.println("Mode derbyclient derby database selected");
			System.out.println("Driver: org.apache.derby.jdbc.ClientDriver");
			System.out.println("protocol: jdbc:derby://localhost:1527/ ");
			this.printLine();
		} else {
			System.out.println("error mode input, please input again");
			this.printLine();
			selectMode();
		}

	}

	public void printLine() {
		System.out.println("---------------------------------------------");
	}

	public String getFramework() {
		return framework;
	}

	public void setFramework(String framework) {
		this.framework = framework;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

}

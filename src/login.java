

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

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
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

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	private void setProperties() {
		props.put("user", "admin");
		props.put("password", "123456");
	}

	private void createDB() throws SQLException {
		conn = DriverManager.getConnection(protocol + dbName + ";create=true",
				props);
	}

	private void add(String name, String password) throws SQLException {

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Aucion</title>");
		out.println("<link rel=\"stylesheet\" href=\"style.css\">");
		out.println("</head>");
out.println("<body background=\"images\\background.jpg\" style=\"background-repeat:no-repeat\">");
		out.println("<center>");
		out.println("<div id=\"container\"> <div id=\"header\"> <h1>Auction System</h1> </div>");

		out.println("<div class=\"login\">");
		if (request.getParameter("name") == null
				|| request.getParameter("name").equals("")) {
			out.println("<form name=\"login\" action=\"login\" method=\"post\">"
					+ "<font color=#0000FF>Username:</font>&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"name\"/><br>"
					+ "<font color=#0000FF>Password:&nbsp;&nbsp;&nbsp;</font><input type=\"password\" name=\"password\"/><br>"
					+ "<input type=\"submit\" value=\"Submit\" />"
					+ "<input type=\"reset\" value=\"reset\"/>" + "</form>");
			out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
					+ "<a href=\"register\">register to a new user</a><br>");
		} else {
			try {
				Class.forName(driver).newInstance();
			} catch (ClassNotFoundException cnfe) {
				System.err
						.println("\nUnable to load the JDBC driver " + driver);
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
				connectTable();
				switch (searchName(request.getParameter("name"),
						request.getParameter("password"))) {
				case 0: 
					out.println("<font color=#FF0000><h3>There is on such username, please check your name</h3></font><br>");
					out.println("<form name=\"login\" action=\"login\" method=\"post\">"
+ "<font color=#0000FF>Username:</font>&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"name\"/><br>"
+ "<font color=#0000FF>Password:</font>&nbsp;&nbsp;&nbsp;<input type=\"password\" name=\"password\"/><br>"
							+ "<input type=\"submit\" value=\"Submit\" />"
							+ "<input type=\"reset\" value=\"reset\"/>" + "</form>");
					out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
							+ "<a href=\"register\">register to a new user</a><br>");
					break;
				case 1:
					HttpSession session=request.getSession(true);
					session.setAttribute("name", request.getParameter("name"));
					out.println("<h3>Welcome back!</h3>"+request.getParameter("name")+"<br>");
					out.println("The page will AutoJump in few seconds");
					response.sendRedirect("index.jsp");
					break;
				case 2:
					out.println("<font color=#FF0000><h3>Your password is wrong, please try again!</h3></font><br>");
					out.println("<form name=\"login\" action=\"login\" method=\"post\">"
							+ "<font color=#0000FF>Username:</font>&nbsp;&nbsp;&nbsp;<input type=\"text\" " +
							"name=\"name\" value=\""+request.getParameter("name")+"\"/><br>"
							+ "<font color=#0000FF>Password:&nbsp;&nbsp;&nbsp;</font><input type=\"password\" name=\"password\"/><br>"
							+ "<input type=\"submit\" value=\"Submit\" />"
							+ "<input type=\"reset\" value=\"reset\"/>" + "</form>");
					out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
							+ "<a href=\"register\">register to a new user</a><br>");
					break;
				default:
					break;
				};
			} catch (SQLException sqle) {
			
			} finally {
				try {
					if (rs != null) {
						rs.close();
						rs = null;
					}
				} catch (SQLException sqle) {
				}
				try {
					if (conn != null) {
						conn.close();
						conn = null;
					}
				} catch (SQLException sqle) {
				}
			}
		}
		

		out.println("</div>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
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

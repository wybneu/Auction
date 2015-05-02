
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class register
 */
@WebServlet("/register")
public class register extends HttpServlet {
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
    public register() {
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

    private void createDB() throws SQLException {
        conn = DriverManager.getConnection(protocol + dbName + ";create=true",
                props);
        System.out.println("fdsfsdfsdfsdfsdfsdfsdfsdf");
    }

    private void add(String name, String password) throws SQLException {

        try {
            if (name.equals(null))
                return;
            psInsert.setString(1, name);
            psInsert.setString(2, password);
            psInsert.executeUpdate();
            conn.commit();
            return;
        } catch (NumberFormatException e) {
            System.out.println("Wrong input type, please input again");
        }
    }

    private void setProperties() {
        props.put("user", "admin");
        props.put("password", "123456");
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
        if (!set.contains("users".toUpperCase())) {
            s = conn.createStatement();
            s.execute("create table "
                    + tableName
                    + " (NAME varchar(20) NOT NULL UNIQUE, PASSWORD varchar(20) NOT NULL)");
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
        out.println("<div id=\"container\"> <div id=\"header\"> <h1>Auction"
                + " System</h1> </div>");

        out.println("<div class=\"register\">");
        if (request.getParameter("name") == null
                || request.getParameter("name").equals("")) {
            out.println("<form name=\"register\" action=\"register\" method=\"post\">"
                    + "<font color=#0000FF>Username</font><br>(between 4-20 characters)<br>"
                    + "<input type=\"text\" name=\"name\"/><br>"
                    + "<font color=#0000FF>Creat a password</font><br>(between 4-20 characters)<br>"
                    + "<input type=\"password\" name=\"password1\"/><br>"
                    + "<font color=#0000FF>Confirm your password</font><br>(between 4-20 characters)<br>"
                    + "<input type=\"password\" name=\"password2\"/><br>"
                    + "<input type=\"submit\" value=\"Submit\" />"
                    + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
            out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                    + "<a href=\"login\">click here to login</a><br>");
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
                this.setTools();
                switch (userName(request.getParameter("name"),
                        request.getParameter("password1"),
                        request.getParameter("password2"))) {
                case 0:
                    out.println("<font color=#FF0000><h3>The username should be between 4-20 characters.</h3></font><br>");
                    out.println("<form name=\"register\" action=\"register\" method=\"post\">"
                            + "<font color=#0000FF>Username</font><br>(between 4-20 characters)<br>"
                            + "<input type=\"text\" name=\"name\"/><br>"
                            + "<font color=#0000FF>Creat a password</font><br>(between 4-20 characters)<br>"
                            + "<input type=\"password\" name=\"password1\"/><br>"
                            + "<font color=#0000FF>Confirm your password</font><br>(between 4-20 characters)<br>"
                            + "<input type=\"password\" name=\"password2\"/><br>"
                            + "<input type=\"submit\" value=\"Submit\" />"
                            + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
                    out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                            + "<a href=\"register\">register to a new user</a><br>");
                    break;
                case 1:
                    out.println("<font color=#FF0000><h3>The password should be between 4-20 characters.</h3></font><br>");
                    out.println("<form name=\"register\" action=\"register\" method=\"post\">"
                            + "<font color=#0000FF>Username</font><br>(between 4-20 characters)<br>"
                            + "<input type=\"text\" name=\"name\"/><br>"
                            + "<font color=#0000FF>Creat a password</font><br>(between 4-20 characters)<br>"
                            + "<input type=\"password\" name=\"password1\"/><br>"
                            + "<font color=#0000FF>Confirm your password</font><br>(between 4-20 characters)<br>"
                            + "<input type=\"password\" name=\"password2\"/><br>"
                            + "<input type=\"submit\" value=\"Submit\" />"
                            + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
                    out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                            + "<a href=\"register\">register to a new user</a><br>");
                    break;
                case 2:
                    out.println("<font color=#FF0000><h3>Someone already has that username. Try another?</h3></font><br>");
                    out.println("<form name=\"register\" action=\"register\" method=\"post\">"
                            + "<font color=#0000FF>Username</font><br>(between 4-20 characters)<br>"
                            + "<input type=\"text\" name=\"name\"/><br>"
                            + "<font color=#0000FF>Creat a password</font><br>(between 4-20 characters)<br>"
                            + "<input type=\"password\" name=\"password1\"/><br>"
                            + "<font color=#0000FF>Confirm your password</font><br>(between 4-20 characters)<br>"
                            + "<input type=\"password\" name=\"password2\"/><br>"
                            + "<input type=\"submit\" value=\"Submit\" />"
                            + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
                    out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                            + "<a href=\"register\">register to a new user</a><br>");
                    break;
                case 3:
                    try {
                        add(request.getParameter("name"),
                                    request.getParameter("password1"));
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    HttpSession session = request.getSession(true);
                    session.setAttribute("name", request.getParameter("name"));
                    out.println("<h3>Registration Successful!</h3>"
                            + request.getParameter("name") + "<br>");
                    out.println("The page will AutoJump in few seconds");
                    response.sendRedirect("index.jsp");
                    break;
                case 4:
                    out.println("<font color=#FF0000><h3>Confirm password should be the same as the password.</h3></font><br>");
                    out.println("<form name=\"register\" action=\"register\" method=\"post\">"
                            + "<font color=#0000FF>Username</font><br>(between 4-20 characters)<br>"
                            + "<input type=\"text\" name=\"name\"/><br>"
                            + "<font color=#0000FF>Creat a password</font><br>(between 4-20 characters)<br>"
                            + "<input type=\"password\" name=\"password1\"/><br>"
                            + "<font color=#0000FF>Confirm your password</font><br>(between 4-20 characters)<br>"
                            + "<input type=\"password\" name=\"password2\"/><br>"
                            + "<input type=\"submit\" value=\"Submit\" />"
                            + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
                    out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                            + "<a href=\"register\">register to a new user</a><br>");
                    break;
                default:
                    break;
                }
           
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
    
    private int userName(String name, String password1, String password2) throws SQLException {
            if ((name.length() > 20) || (name.length() < 4)) {
                return 0;
            }
            if ((password1.length() > 20) || (password1.length() < 4)) {
                return 1;
            }
            if (password1.equals(password2)) {
               
                
                s = conn.createStatement();
                rs = s.executeQuery("select * from " + tableName
                        + " ORDER BY NAME");
                if(!rs.next()) return 3;
                do {
                    if (rs.getString("NAME").equalsIgnoreCase(name))
                        return 2;
                } while (rs.next());
                return 3;
                
            }
            return 4;
       
    }
}




import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class logout
 */
@WebServlet("/logout")
public class logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Aucion</title>");
		out.println("<link rel=\"stylesheet\" href=\"style.css\">");
		out.println("</head>");
		out.println("<body class=\"twoColLiqLtHdr\">");
		out.println("<center>");
		out.println("<div id=\"container\"> <div id=\"header\"> <h1>Auction System</h1> </div>");

		out.println("<div class=\"login\">");
		HttpSession session=request.getSession(true);
		session.setAttribute("name",null);
		response.sendRedirect("index.jsp?pages=display");

		out.println("</div>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

}

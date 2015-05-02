import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Auction;
import model.AuctionDB;

/**
 * Servlet implementation class addNewAuction
 */
@WebServlet("/addNewAuction")
public class addNewAuction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public addNewAuction() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doPost(request, response);
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
        out.println("<body background=\"images\\lotus.jpg\" style=\"background-repeat:no-repeat\">");
        out.println("<center>");
        out.println("<div id=\"container\"> <div id=\"header\"> <h1>Auction System</h1> </div>");

        out.println("<div class=\"login\">");
        HttpSession session = request.getSession(true);
        if (session.getAttribute("name") == null) {
            out.print("<br /><br /><br /><br /><center><font color=#FF0000>"
                    + "<h1>You need to login in to use this function!!!</h1></font>"
                    + "<br><br> <a href=\"index.jsp\"><h3>Browser Auctions</h3></a></center>");
        } else if (request.getParameter("name") == "") {
            out.println("<center><font color=#FF0000><h3>The auction name cannot be empty.</h3></font>");
            out.println("<center><h3>Input your new auction</h3>");
            out.println("<form name=\"addNewAuction\" action=\"addNewAuction\" method=\"post\">"
                    + "Name(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    + "<input type=\"text\" name=\"name\"/><br>"
                    + "Description(*):&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"description\"/><br>"
                    + "Reserver Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"reserver\"/><br>"
                    + "Buy Out Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"buyout\"/><br>"
                    + "Starting Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"startprice\" value=\"0\"/><br>"
                    + "Auction Time(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>"
                    + "Days:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"day\"/><br>"
                    + "Hours:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"hour\"/><br>"
                    + "Minutes:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"min\"/><br>"
                    + "Seconds:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"second\"/><br>"
                    + "<input type=\"submit\" value=\"Submit\" />"
                    + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
            out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                    + "<br></center>");

        } else if (request.getParameter("name") == null) {
            out.println("<center><h3>Input your new auction</h3>");
            out.println("<form name=\"addNewAuction\" action=\"addNewAuction\" method=\"post\">"
                    + "Name(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    + "<input type=\"text\" name=\"name\"/><br>"
                    + "Description(*):&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"description\"/><br>"
                    + "Reserver Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"reserver\"/><br>"
                    + "Buy Out Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"buyout\"/><br>"
                    + "Starting Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"startprice\" value=\"0\"/><br>"
                    + "Auction Time(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>"
                    + "Days:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"day\"/><br>"
                    + "Hours:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"hour\"/><br>"
                    + "Minutes:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"min\"/><br>"
                    + "Seconds:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"second\"/><br>"
                    + "<input type=\"submit\" value=\"Submit\" />"
                    + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
            out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                    + "<br></center>");

        } else if (request.getParameter("description") == "") {
            out.println("<center><font color=#FF0000><h3>The description cannot be empty.</h3></font>");
            out.println("<center><h3>Input your new auction</h3>");
            out.println("<form name=\"addNewAuction\" action=\"addNewAuction\" method=\"post\">"
                    + "Name(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    + "<input type=\"text\" name=\"name\"/><br>"
                    + "Description(*):&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"description\"/><br>"
                    + "Reserver Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"reserver\"/><br>"
                    + "Buy Out Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"buyout\"/><br>"
                    + "Starting Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"startprice\" value=\"0\"/><br>"
                    + "Auction Time(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>"
                    + "Days:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"day\"/><br>"
                    + "Hours:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"hour\"/><br>"
                    + "Minutes:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"min\"/><br>"
                    + "Seconds:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"second\"/><br>"
                    + "<input type=\"submit\" value=\"Submit\" />"
                    + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
            out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                    + "<br></center>");

        } else {
            AuctionDB auctionDB = new AuctionDB();
            auctionDB.connectAcutionDB();
            int auctionDay = 0;
            int auctionHour = 0;
            int auctionMin = 0;
            int auctionSecond = 0;
            int auctionReserver = 0;
            int auctionBuyout = 0;
            int auctionStartprice = 0;
            int status = 0;
            try {
                if (request.getParameter("reserver") != "") {
                    auctionReserver = Integer.valueOf(request
                            .getParameter("reserver"));
                }
            } catch (Exception e) {
                out.println("<center><font color=#FF0000><h3>"
                        + "The reserver price is invalid input, please enter an valid number.</h3></font>");
                out.println("<center><h3>Input your new auction</h3>");
                out.println("<form name=\"addNewAuction\" action=\"addNewAuction\" method=\"post\">"
                        + "Name(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<input type=\"text\" name=\"name\"/><br>"
                        + "Description(*):&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"description\"/><br>"
                        + "Reserver Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"reserver\"/><br>"
                        + "Buy Out Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"buyout\"/><br>"
                        + "Starting Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"startprice\" value=\"0\"/><br>"
                        + "Auction Time(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>"
                        + "Days:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"day\"/><br>"
                        + "Hours:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"hour\"/><br>"
                        + "Minutes:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"min\"/><br>"
                        + "Seconds:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"second\"/><br>"
                        + "<input type=\"submit\" value=\"Submit\" />"
                        + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
                out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                        + "<br></center>");
                status = 1;
            }
            try {
            if (request.getParameter("buyout") != "") {
                    auctionBuyout = Integer.valueOf(request
                            .getParameter("buyout"));
                }
            } catch (Exception e) {
                if (status == 0){
                out.println("<center><font color=#FF0000><h3>"
                        + "The buyout price is invalid input, please enter an valid number.</h3></font>");
                out.println("<center><h3>Input your new auction</h3>");
                out.println("<form name=\"addNewAuction\" action=\"addNewAuction\" method=\"post\">"
                        + "Name(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<input type=\"text\" name=\"name\"/><br>"
                        + "Description(*):&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"description\"/><br>"
                        + "Reserver Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"reserver\"/><br>"
                        + "Buy Out Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"buyout\"/><br>"
                        + "Starting Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"startprice\" value=\"0\"/><br>"
                        + "Auction Time(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>"
                        + "Days:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"day\"/><br>"
                        + "Hours:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"hour\"/><br>"
                        + "Minutes:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"min\"/><br>"
                        + "Seconds:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"second\"/><br>"
                        + "<input type=\"submit\" value=\"Submit\" />"
                        + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
                out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                        + "<br></center>");
                status = 1;
                }
            }
            try {
                if (request.getParameter("startprice") != "") {
                    auctionStartprice = Integer.valueOf(request
                            .getParameter("startprice"));
                }
            } catch (Exception e) {
                if (status == 0){
                out.println("<center><font color=#FF0000><h3>"
                        + "The starting price is invalid input, please enter an valid number.</h3></font>");
                out.println("<center><h3>Input your new auction</h3>");
                out.println("<form name=\"addNewAuction\" action=\"addNewAuction\" method=\"post\">"
                        + "Name(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<input type=\"text\" name=\"name\"/><br>"
                        + "Description(*):&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"description\"/><br>"
                        + "Reserver Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"reserver\"/><br>"
                        + "Buy Out Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"buyout\"/><br>"
                        + "Starting Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"startprice\" value=\"0\"/><br>"
                        + "Auction Time(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>"
                        + "Days:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"day\"/><br>"
                        + "Hours:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"hour\"/><br>"
                        + "Minutes:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"min\"/><br>"
                        + "Seconds:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"second\"/><br>"
                        + "<input type=\"submit\" value=\"Submit\" />"
                        + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
                out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                        + "<br></center>");
                status = 1;
                }
            }
            if ((auctionBuyout < auctionReserver) && (status == 0)) {
                out.println("<center><font color=#FF0000><h3>The buyout price should not be less than reserver price.</h3></font>");
                out.println("<center><h3>Input your new auction</h3>");
                out.println("<form name=\"addNewAuction\" action=\"addNewAuction\" method=\"post\">"
                        + "Name(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<input type=\"text\" name=\"name\"/><br>"
                        + "Description(*):&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"description\"/><br>"
                        + "Reserver Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"reserver\"/><br>"
                        + "Buy Out Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"buyout\"/><br>"
                        + "Starting Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"startprice\" value=\"0\"/><br>"
                        + "Auction Time(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>"
                        + "Days:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"day\"/><br>"
                        + "Hours:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"hour\"/><br>"
                        + "Minutes:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"min\"/><br>"
                        + "Seconds:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"second\"/><br>"
                        + "<input type=\"submit\" value=\"Submit\" />"
                        + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
                out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                        + "<br></center>");
                status = 1;
            }
            if ((auctionBuyout < auctionStartprice) && (status == 0)) {
                out.println("<center><font color=#FF0000><h3>The buyout price should not be less than starting price.</h3></font>");
                out.println("<center><h3>Input your new auction</h3>");
                out.println("<form name=\"addNewAuction\" action=\"addNewAuction\" method=\"post\">"
                        + "Name(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<input type=\"text\" name=\"name\"/><br>"
                        + "Description(*):&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"description\"/><br>"
                        + "Reserver Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"reserver\"/><br>"
                        + "Buy Out Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"buyout\"/><br>"
                        + "Starting Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"startprice\" value=\"0\"/><br>"
                        + "Auction Time(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>"
                        + "Days:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"day\"/><br>"
                        + "Hours:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"hour\"/><br>"
                        + "Minutes:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"min\"/><br>"
                        + "Seconds:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"second\"/><br>"
                        + "<input type=\"submit\" value=\"Submit\" />"
                        + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
                out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                        + "<br></center>");
                status = 1;
            }
            try {
                if (request.getParameter("day") != "") {
                    auctionDay = Integer.valueOf(request.getParameter("day"));
                }
                if (request.getParameter("hour") != "") {
                    auctionHour = Integer.valueOf(request.getParameter("hour"));
                }
                if (request.getParameter("min") != "") {
                    auctionMin = Integer.valueOf(request.getParameter("min"));
                }
                if (request.getParameter("second") != "") {
                    auctionSecond = Integer.valueOf(request
                            .getParameter("second"));
                }
            } catch (Exception e) {
                if (status == 0){
                out.println("<center><font color=#FF0000><h3>"
                        + "The auction time is invalid input, please enter an valid number.</h3></font>");
                out.println("<center><h3>Input your new auction</h3>");
                out.println("<form name=\"addNewAuction\" action=\"addNewAuction\" method=\"post\">"
                        + "Name(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<input type=\"text\" name=\"name\"/><br>"
                        + "Description(*):&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"description\"/><br>"
                        + "Reserver Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"reserver\"/><br>"
                        + "Buy Out Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"buyout\"/><br>"
                        + "Starting Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"startprice\" value=\"0\"/><br>"
                        + "Auction Time(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>"
                        + "Days:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"day\"/><br>"
                        + "Hours:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"hour\"/><br>"
                        + "Minutes:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"min\"/><br>"
                        + "Seconds:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"second\"/><br>"
                        + "<input type=\"submit\" value=\"Submit\" />"
                        + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
                out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                        + "<br></center>");
                status = 1;
                }
            }
            if ((auctionDay == 0) && (auctionHour == 0) && (auctionMin == 0)
                    && (auctionSecond == 0)) {
                if (status == 0){
                out.println("<center><font color=#FF0000><h3>The auction time cannot be empty, please fill the time.</h3></font>");
                out.println("<center><h3>Input your new auction</h3>");
                out.println("<form name=\"addNewAuction\" action=\"addNewAuction\" method=\"post\">"
                        + "Name(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<input type=\"text\" name=\"name\"/><br>"
                        + "Description(*):&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"description\"/><br>"
                        + "Reserver Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"reserver\"/><br>"
                        + "Buy Out Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"buyout\"/><br>"
                        + "Starting Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"startprice\" value=\"0\"/><br>"
                        + "Auction Time(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>"
                        + "Days:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"day\"/><br>"
                        + "Hours:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"hour\"/><br>"
                        + "Minutes:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"min\"/><br>"
                        + "Seconds:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"second\"/><br>"
                        + "<input type=\"submit\" value=\"Submit\" />"
                        + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
                out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                        + "<br></center>");
                status = 1;
                }

            }
            if ((((auctionDay * 24 + auctionHour) * 60) + auctionMin) * 60
                    + auctionSecond < 600) {
                if (status == 0){
                out.println("<center><font color=#FF0000><h3>The auction time cannot less than 10 minutes.</h3></font>");
                out.println("<center><h3>Input your new auction</h3>");
                out.println("<form name=\"addNewAuction\" action=\"addNewAuction\" method=\"post\">"
                        + "Name(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<input type=\"text\" name=\"name\"/><br>"
                        + "Description(*):&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"description\"/><br>"
                        + "Reserver Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"reserver\"/><br>"
                        + "Buy Out Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"buyout\"/><br>"
                        + "Starting Price:&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"startprice\" value=\"0\"/><br>"
                        + "Auction Time(*):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>"
                        + "Days:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"day\"/><br>"
                        + "Hours:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"hour\"/><br>"
                        + "Minutes:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"min\"/><br>"
                        + "Seconds:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" name=\"second\"/><br>"
                        + "<input type=\"submit\" value=\"Submit\" />"
                        + "<input type=\"reset\" value=\"reset\"/>" + "</form>");
                out.println("<a href=\"index.jsp\">return to the homepage</a><br>"
                        + "<br></center>");
                status = 1;
                }
            }
            if (status == 0){
            auctionDB
                    .add((String) request.getParameter("name"),
                            (String) request.getParameter("description"),
                            auctionReserver,
                            auctionBuyout,
                            auctionStartprice,
                            new Timestamp(
                                    System.currentTimeMillis()
                                            + Long.valueOf((((auctionDay * 24 + auctionHour) * 60) + auctionMin)
                                                    * 60 + auctionSecond)
                                            * 1000), (String) session
                                    .getAttribute("name"));
            auctionDB.closeAuctionDB();
            out.println("<center><br><br><h2>Add new auctions successfully!</h2>");
            out.println("<a href=\"index.jsp\">Return to the homepage</a><br>");
            }
        }
        out.println("</div>");
        out.println("</center>");
        out.println("</body>");
        out.println("</html>");
    }
}

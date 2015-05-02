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

import org.apache.catalina.Session;

import controller.Monitor;

import model.Auction;
import model.AuctionDB;

/**
 * Servlet implementation class bidAuction
 */
@WebServlet("/bidAuction")
public class bidAuction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public bidAuction() {
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
		out.println("<body background=\"images\\bidground.jpg\" style=\"background-repeat:no-repeat\">");
		out.println("<center>");
		out.println("<div id=\"container\"> <div id=\"header\"> <h1>Auction System</h1> </div>");
		Monitor monitor = new Monitor();
		monitor.verify();
		out.println("<div class=\"login\">");
		HttpSession session = request.getSession(true);
		Boolean login = true;
		if (session.getAttribute("name") == null)
			login = false;
		String username;
		if (login) {
			username = session.getAttribute("name").toString();
		} else
			username = "Guest";
		switch (verify(request)) {
		case 1:
			out.print("<br /><br /><br /><br /><h1>Item Error</h1><br><br> <a href=\"index.jsp\"><h2>Browser Auctions</h2></a>");
			break;
		case 2:

			AuctionDB auctionDB = new AuctionDB();
			auctionDB.connectAcutionDB();
			int auctionID = Integer.valueOf(request.getParameter("auction"));
			Auction auction = auctionDB.getAuctionByID(auctionID);
			int minimumPrice = auction.getCurrentPrice();
			int bidNumber;
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			String bidMessage = "";
			if (minimumPrice == (int) (auction.getCurrentPrice() * 1.05)) {
				minimumPrice += 1;

			} else {
				minimumPrice = (int) (auction.getCurrentPrice() * 1.05);
				if ((auction.getCurrentPrice() * 1.05 - minimumPrice) != 0) {
					minimumPrice = minimumPrice + 1;
				}
			}
			;
			if (request.getParameter("bid") == null) {

			} else {
				try {
					bidNumber = Integer.valueOf(request.getParameter("bid"));
					if (bidNumber < minimumPrice) {
						bidMessage = "Your minimum bid should no less than "
								+ minimumPrice;
					} else {
						bidMessage = "You bid success by the price "
								+ bidNumber;
						Timestamp auctionTime = auctionDB
								.getAuctionTime(auctionID);
						currentTime = new Timestamp(System.currentTimeMillis());
						if (auction.getSold() == 1) {
							if (auctionTime.getTime() > currentTime.getTime()) {
								if ((auctionTime.getTime() - currentTime
										.getTime()) < 1 * 60 * 1000) {
									auctionDB
											.updateTime(
													new Timestamp(
															auctionTime
																	.getTime() + 10 * 60 * 1000),
													auctionID);
								}
								auctionDB.updateWinner(username, auctionID);
								auctionDB.updateCurrentPrice(bidNumber,
										auctionID);
								if (bidNumber >= auction.getBuyOut()
										&& auction.getBuyOut() > 0) {
									bidMessage = "<h2>Congratulation! You Got It! Buy Out It! </h2> ";
									auctionDB.updateSold(0, auctionID);
									auctionDB
											.updateTime(currentTime, auctionID);
								}
							} else {
								monitor.verify();
								bidMessage = "This Auction has been Time Out! ";
							}
						} else if (auction.getSold() == 0) {
							bidMessage = "This Auction has been Sold Out!";
						} else {

						}

						// auctionID);
						// auctionDB.updateCurrentPrice(bidNumber,
						// auctionID);
					}
				} catch (NumberFormatException e) {
					bidMessage = "A invaild bid has been given! ";
				} catch (SQLException e) {
					bidMessage = "Bid failed! try agin?";
				}
			}
			auction = auctionDB.getAuctionByID(auctionID);
			minimumPrice = auction.getCurrentPrice();
			if (minimumPrice == (int) (auction.getCurrentPrice() * 1.05)) {
				minimumPrice += 1;

			} else {
				minimumPrice = (int) (auction.getCurrentPrice() * 1.05);
				if ((auction.getCurrentPrice() * 1.05 - minimumPrice) != 0) {
					minimumPrice = minimumPrice + 1;
				}
			}
			out.println("<br><h1><b>" + auction.getName() + "</b></h1>");
			out.println("<p>" + auction.getDescription()
					+ "</p><br><b>More Details:</b><br>");

			if (auction.getSold() == 1) {
				Timestamp tempTimestamp = new Timestamp(auction
						.getAuctionTime().getTime()
						- System.currentTimeMillis());
				out.println("<table border=\"0\" align=\"center\">"
						+ "<tr><td>Current Price:  </td>" + "<td>"
						+ auction.getCurrentPrice()
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>Owner:  </td>"
						+ "<td>"
						+ auction.getUserName()
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>Current Winner:  </td>"
						+ "<td>"
						+ auction.getWinner()
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>Start Price: </td>"
						+ "<td>"
						+ auction.getStartPrice()
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>By Out Price:  </td>"
						+ "<td>"
						+ auction.getBuyOut()
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>minimum bid price:  </td>"
						+ "<td>"
						+ minimumPrice
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>Start Time:   </td>"
						+ "<td>"
						+ auction.getStartTime()
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>current Time:   </td>"
						+ "<td>"
						+ new Timestamp(System.currentTimeMillis())
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>Time Left:   </td>"
						+ "<td>"
						+ ((auction.getAuctionTime().getTime()-System.currentTimeMillis())/1000/60) 
						+ " Minutes</td>"
						+ "</tr>"
						+ "<tr><td>finish Time:  </td>"
						+ "<td>"
						+ auction.getAuctionTime() + "</td>" + "</table>");
				if (!login) {
					bidMessage = "You need to login to bid this auction";
				}
				out.println("<br><font color=\"red\">" + bidMessage
						+ "</font></br>");
				if (!login) {
					out.println("<br><form name=\"bid\" action=\"bidAuction\">"
							+ "<input type=\"hidden\" name=\"auction\" "
							+ "value=\""
							+ auctionID
							+ "\">"
							+ "Your Bid: <input type=\"text\" value=\""
							+ minimumPrice
							+ "\" name=\"bid\" disabled=\"disabled\">"
							+ "<input type=\"submit\" value=\"Bid It\" disabled=\"disabled\">"
							+ "</form>");
				} else {
					out.println("<br><form name=\"bid\" action=\"bidAuction\">"
							+ "<input type=\"hidden\" name=\"auction\" "
							+ "value=\"" + auctionID + "\">"
							+ "Your Bid: <input type=\"text\" value=\""
							+ minimumPrice + "\" name=\"bid\">"
							+ "<input type=\"submit\" value=\"Bid It\">"
							+ "</form>");
				}
			} else if (auction.getSold() == 0) {
				out.println("<h2><font color=\"red\">SOLD</font></h2>");
				String winMethod = (auction.getCurrentPrice() >= auction
						.getBuyOut()) ? "Buy Out" : "Got Reserver";
				out.println("<table border=\"0\" align=\"center\">"
						+ "<tr><td>Finished Price:  </td>" + "<td>"
						+ auction.getCurrentPrice()
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>Seller:  </td>"
						+ "<td>"
						+ auction.getUserName()
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>Winner:  </td>"
						+ "<td>"
						+ auction.getWinner()
						+ "</td>"
						+ "<tr><td>Method:  </td>"
						+ "<td>"
						+ winMethod
						+ "</td>"
						+ "</tr>"
						+ "</tr>"
						+ "<tr><td>Start Price: </td>"
						+ "<td>"
						+ auction.getStartPrice()
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>Start Time:   </td>"
						+ "<td>"
						+ auction.getStartTime()
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>finish Time:  </td>"
						+ "<td>"
						+ auction.getAuctionTime()
						+ "</td>"
						+ "</tr>"
						+ "</table>");
				out.println("<br>" + bidMessage + "</br>");

			} else {
				out.println("<h2><font color=\"red\">Failed</font></h2>");
				String failMethod = (auction.getCurrentPrice() == auction
						.getStartPrice()) ? "No One Bid"
						: "No One Got the Reserver";
				out.println("<table border=\"0\" align=\"center\">"
						+ "<tr><td>Finished Price:  </td>" + "<td>"
						+ auction.getCurrentPrice()
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>Seller:  </td>"
						+ "<td>"
						+ auction.getUserName()
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>Failed reason:  </td>"
						+ "<td>"
						+ failMethod
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>Start Price: </td>"
						+ "<td>"
						+ auction.getStartPrice()
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>Start Time:   </td>"
						+ "<td>"
						+ auction.getStartTime()
						+ "</td>"
						+ "</tr>"
						+ "<tr><td>finish Time:  </td>"
						+ "<td>"
						+ auction.getAuctionTime()
						+ "</td>"
						+ "</tr>"
						+ "</table>");
				out.println("<br>" + bidMessage + "</br>");
			}
			auctionDB.closeAuctionDB();
			out.println("<a href=\"index.jsp\">return to the homepage</a><br>");

			break;
		case 3:
			out.print("<br /><br /><br /><br /><h1>Item Error</h1><br><br> <a href=\"index.jsp\"><h2>Browser Auctions</h2></a>");
			break;
		case 4:

			break;
		}

		out.println("</div>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

	public int verify(HttpServletRequest request) {

		if ((request.getParameter("auction") == null)
				&& (request.getParameter("bid") == null))
			return 1;
		return 2;
	}

}

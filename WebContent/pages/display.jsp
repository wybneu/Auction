<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,model.AuctionDB,model.Auction,java.util.*,model.MyUtils,controller.Monitor"
	errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>display</title>
</head>
<body>
	<%
		Monitor monitor = new Monitor();
			monitor.verify();
			String keyword = (request.getParameter("keyword") == null) ? ""
			: request.getParameter("keyword");
			String user = (request.getParameter("user") == null) ? "" : request
			.getParameter("user");
			String aera;
			String pages;
			if (session.getAttribute("aera") == null)
		session.setAttribute("aera", "current");
			session.setAttribute("aera",
			(request.getParameter("aera") == null) ? session
					.getAttribute("aera").toString() : request
					.getParameter("aera").toString());
			aera = session.getAttribute("aera").toString();
			if (session.getAttribute("pages") == null) {
		session.setAttribute("pages", "display");
			}
			session.setAttribute("pages",
			(request.getParameter("pages") == null) ? session
					.getAttribute("pages").toString() : request
					.getParameter("pages").toString());
			pages = session.getAttribute("pages").toString();
			String pagesinfo;
			if (pages.equalsIgnoreCase("display")) {
		pagesinfo = "Display in " + aera + " aera";
			} else if (pages.equalsIgnoreCase("currentAuction")) {
		pagesinfo = "My Current Auction";
			} else if (pages.equalsIgnoreCase("pastAuction")) {
		pagesinfo = "My Success Auction";
			} else if (pages.equalsIgnoreCase("allAuction")) {
		pagesinfo = "ALL My Auctions";
			} else {
		pagesinfo = "Winned Auction";
			}
			if (session.getAttribute("name") == null) {
		session.setAttribute("pages", "display");
		pages = "display";
			}
	%>

	<div class="search">

		<form name="search" action="index.jsp" method="Post">
			Key Word:&nbsp;<input type="text" name="keyword" value="<%=keyword%>">
			</input>&nbsp; User:&nbsp;<input type="text" name="user" value="<%=user%>"></input>
			<input type="hidden" name="sort"
				value="<%=(request.getParameter("sort") == null) ? "desctime"
					: request.getParameter("sort")%>=" />
			<%
				if (session.getAttribute("name") != null) {
			%>
			<select name="aera">
				<option value="current">Current</option>
				<option value="completed">Completed</option>
				<option value="all">All</option>
			</select>
			<%
				}
			%>
			<input type="submit" value="Search"></input>
		</form>
		<br> Sort by : <a
			href="index.jsp?sort=asctime&keyword=<%=keyword%>&user=<%=user%>">Increase
			Time</a> | <a
			href="index.jsp?sort=desctime&keyword=<%=keyword%>&user=<%=user%>">Decrease
			Time</a> | <a
			href="index.jsp?sort=ascbid&keyword=<%=keyword%>&user=<%=user%>">Increase
			Bid</a> | <a
			href="index.jsp?sort=descbid&keyword=<%=keyword%>&user=<%=user%>">Decrease
			Bid</a> | Model :
		<%=pagesinfo%>
	</div>
	<div id="mainContent">
		<%
			AuctionDB auctionDB = new AuctionDB();
			auctionDB.connectAcutionDB();
			ArrayList<Auction> auctions;
			System.out.println(pages);
			if (pages == null || pages == ""
					|| pages.equalsIgnoreCase("display")) {
				if (request.getParameter("keyword") == null
						|| request.getParameter("keyword").toString()
								.equals("")) {
					if (request.getParameter("user") == null
							|| request.getParameter("user").toString()
									.equals("")) {
						auctions = auctionDB.displayByID();
					} else {
						auctions = auctionDB.searchUser(request.getParameter(
								"user").toString());
					}
				} else {
					if (request.getParameter("user") == null
							|| request.getParameter("user").toString()
									.equals("")) {
						auctions = auctionDB.searchKeyword(request
								.getParameter("keyword").toString());
					} else {
						auctions = auctionDB.searchKeywordByUser(request
								.getParameter("keyword").toString(), request
								.getParameter("user"));
					}
				}
			} else if (pages.equalsIgnoreCase("currentAuction")) {

				if (request.getParameter("keyword") == null
						|| request.getParameter("keyword").toString()
								.equals("")) {
					if (request.getParameter("user") == null
							|| request.getParameter("user").toString()
									.equals("")) {
						auctions = auctionDB.searchUser(session.getAttribute(
								"name").toString());
					} else {
						auctions = auctionDB.searchUser(request.getParameter(
								"user").toString());
					}
				} else {
					if (request.getParameter("user") == null
							|| request.getParameter("user").toString()
									.equals("")) {
						auctions = auctionDB.searchKeywordByUser(request
								.getParameter("keyword").toString(), session
								.getAttribute("name").toString());
					} else {
						auctions = auctionDB.searchKeywordByUser(request
								.getParameter("keyword").toString(), request
								.getParameter("user").toString());
					}
				}
				aera = "current";

			} else if (pages.equalsIgnoreCase("pastAuction")) {
				if (request.getParameter("keyword") == null
						|| request.getParameter("keyword").toString()
								.equals("")) {
					if (request.getParameter("user") == null
							|| request.getParameter("user").toString()
									.equals("")) {
						auctions = auctionDB.searchUser(session.getAttribute(
								"name").toString());
					} else {
						auctions = auctionDB.searchUser(request.getParameter(
								"user").toString());
					}
				} else {
					if (request.getParameter("user") == null
							|| request.getParameter("user").toString()
									.equals("")) {
						auctions = auctionDB.searchKeywordByUser(request
								.getParameter("keyword").toString(), session
								.getAttribute("name").toString());
					} else {
						auctions = auctionDB.searchKeywordByUser(request
								.getParameter("keyword").toString(), request
								.getParameter("user").toString());
					}
				}
				aera = "completed";
			} else if (pages.equalsIgnoreCase("allAuction")) {
				if (request.getParameter("keyword") == null
						|| request.getParameter("keyword").toString()
								.equals("")) {
					if (request.getParameter("user") == null
							|| request.getParameter("user").toString()
									.equals("")) {
						auctions = auctionDB.searchUser(session.getAttribute(
								"name").toString());
					} else {
						auctions = auctionDB.searchUser(request.getParameter(
								"user").toString());
					}
				} else {
					if (request.getParameter("user") == null
							|| request.getParameter("user").toString()
									.equals("")) {
						auctions = auctionDB.searchKeywordByUser(request
								.getParameter("keyword").toString(), session
								.getAttribute("name").toString());
					} else {
						auctions = auctionDB.searchKeywordByUser(request
								.getParameter("keyword").toString(), request
								.getParameter("user").toString());
					}
				}
				aera = "all";
			} else {
				if (request.getParameter("keyword") == null
						|| request.getParameter("keyword").toString()
								.equals("")) {
					if (request.getParameter("user") == null
							|| request.getParameter("user").toString()
									.equals("")) {
						auctions = auctionDB.searchWinner(session.getAttribute(
								"name").toString());
					} else {
						auctions = auctionDB.searchWinnerByUsername(request
								.getParameter("user").toString(), session
								.getAttribute("name").toString());
					}
				} else {
					if (request.getParameter("user") == null
							|| request.getParameter("user").toString()
									.equals("")) {
						auctions = auctionDB.searchWinnerByKeyword(request
								.getParameter("keyword").toString(), session
								.getAttribute("name").toString());
					} else {
						auctions = auctionDB.searchWinnerByBoth(request
								.getParameter("keyword").toString(), request
								.getParameter("user").toString(), session
								.getAttribute("name").toString());
					}
				}

				aera = "completed";
			}

			//auctions= utils.getAuctionByAscbid(auctions);
		%>
		<table width="75%" border=3>
			<tr>
				<th scope="col" width="25%"><font color="blue">name & Description</font></th>
				<th scope="col" width="20%"><font color="blue">Price($)</font></th>
				<th scope="col" width="25%"><font color="blue">Time </font></th>
				<th scope="col" width="10%"><font color="blue">Seller</font></th>
				<th scope="col" width="10%"><font color="blue">Highest Bidder</font></th>
				<th scope="col" width="10%"><font color="blue">Status</font></th>
			</tr>
			<%
				String message;
				
					if (pages.equals("display")
							&& (request.getParameter("sort") == null
									|| request.getParameter("sort") == "" || request
									.getParameter("sort").toString()
									.equalsIgnoreCase("refresh"))) {

						if (auctions == null) {

						} else {
							int size = auctions.size() - 1;
							for (int i = 0; i < auctions.size(); i++) {
								if (auctions.get(size - i).getSold() == 1) {
			%>
			<tr>
				<td><a
					href="bidAuction?auction=<%=auctions.get(size - i).getID()%>">
						<font size="8"><b><%=auctions.get(size - i).getName()%></b></font><br></a>
					<%=auctions.get(size - i).getDescription()%></td>
				<td><b>Current Price: </b><font color="red"><%=auctions.get(size - i).getCurrentPrice()%></font><br>
				    <b>Start &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Price: </b><%=auctions.get(size - i).getStartPrice()%><br>
				    <b>Buy Out Price: </b><%=auctions.get(size - i).getBuyOut()%>
				</td>
				<td><b>Finish&nbsp; Time: </b><br><font color="red"><%=auctions.get(size - i).getAuctionTime()%></font><br>
				    <b>Start&nbsp;&nbsp; Time: </b><br><%=auctions.get(size - i).getStartTime()%><br>
				    <b>Minutes Left: </b><%=((auctions.get(size - i).getAuctionTime().getTime()-System.currentTimeMillis())/60000)%>
				</td>
				<td><h2><%=auctions.get(size - i).getUserName()%></h2></td>
				<td><h2><b><%=auctions.get(size - i).getWinner()%></b></h2></td>
				<%
					if (auctions.get(size - i).getSold()==0)
							message="<font color=\"red\"><h2>Sold Out</h2></font>";
							else if(auctions.get(size - i).getSold()==1)
								message="<font color=\"blue\"><h2>Available</h2></font>";
								else
									message="Failed";
				%>
				<td><%=message%></td>
			</tr>
			<%
				}
							}
						}

					} else {

						MyUtils tools = new MyUtils();

						if (request.getParameter("sort") == null) {
							auctions = tools.getAuctionByDesctime(auctions);
						} else if (request.getParameter("sort").toString()
								.equalsIgnoreCase("asctime")) {
							auctions = tools.getAuctionByAsctime(auctions);
						} else if (request.getParameter("sort").toString()
								.equalsIgnoreCase("desctime")) {
							auctions = tools.getAuctionByDesctime(auctions);
						} else if (request.getParameter("sort").toString()
								.equalsIgnoreCase("ascbid")) {
							auctions = tools.getAuctionByAscbid(auctions);
						} else if (request.getParameter("sort").toString()
								.equalsIgnoreCase("descbid")) {
							auctions = tools.getAuctionByDescbid(auctions);
						}
						if (auctions == null) {

						} else {
							for (int i = 0; i < auctions.size(); i++) {
								if (session.getAttribute("name") == null
										|| session.getAttribute("name") != null
										&& aera.equalsIgnoreCase("current")) {
									if (auctions.get(i).getSold() != 1) {
									} else {
			%>
			<tr>
				<td><a
					href="bidAuction?auction=<%=auctions.get(i).getID()%>">
						<font size="8"><b><%=auctions.get(i).getName()%></b></font><br></a>
					<%=auctions.get(i).getDescription()%></td>
				<td><b>Current Price: </b><font color="red"><%=auctions.get(i).getCurrentPrice()%></font><br>
				    <b>Start &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Price: </b><%=auctions.get(i).getStartPrice()%><br>
				    <b>Buy Out Price: </b><%=auctions.get(i).getBuyOut()%>
				</td>
				<td><b>Finish&nbsp; Time: </b><br><font color="red"><%=auctions.get(i).getAuctionTime()%></font><br>
				    <b>Start&nbsp;&nbsp; Time: </b><br><%=auctions.get(i).getStartTime()%><br>
				    <b>Minutes Left: </b><%=((auctions.get(i).getAuctionTime().getTime()-System.currentTimeMillis())/60000)%>
				</td>
				<td><h2><%=auctions.get(i).getUserName()%></h2></td>
				<td><h2><b><%=auctions.get(i).getWinner()%></b></h2></td>
				<% if (auctions.get(i).getSold()==0)
					message="<font color=\"red\"><h2>Sold Out</h2></font>";
					else if(auctions.get(i).getSold()==1)
						message="<font color=\"blue\"><h2>Available</h2></font>";
						else
							message="Failed";
					%>
				<td><%=message%></td>
			</tr>
			<%
				}
							} else if (aera.equalsIgnoreCase("completed")) {
								if (auctions.get(i).getSold() == 0) {
			%>
			<tr>
				<td><a
					href="bidAuction?auction=<%=auctions.get(i).getID()%>">
						<font size="8"><b><%=auctions.get(i).getName()%></b></font><br></a>
					<%=auctions.get(i).getDescription()%></td>
				<td><b>Current Price: </b><font color="red"><%=auctions.get(i).getCurrentPrice()%></font><br>
				    <b>Start &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Price: </b><%=auctions.get(i).getStartPrice()%><br>
				    <b>Buy Out Price: </b><%=auctions.get(i).getBuyOut()%>
				</td>
				<td><b>Finish&nbsp; Time: </b><br><font color="red"><%=auctions.get(i).getAuctionTime()%></font><br>
				    <b>Start&nbsp;&nbsp; Time: </b><br><%=auctions.get(i).getStartTime()%><br>
				    <b>Minutes Left: </b><%=((auctions.get(i).getAuctionTime().getTime()-System.currentTimeMillis())/60000)%>
				</td>
				<td><h2><%=auctions.get(i).getUserName()%></h2></td>
				<td><h2><b><%=auctions.get(i).getWinner()%></b></h2></td>
				<% if (auctions.get(i).getSold()==0)
					message="<font color=\"red\"><h2>Sold Out</h2></font>";
					else if(auctions.get(i).getSold()==1)
						message="<font color=\"blue\"><h2>Available</h2></font>";
						else
							message="Failed";
					%>
				<td><%=message%></td>
			</tr>
			<%
				}
							} else {
			%>
			<tr>
				<td><a
					href="bidAuction?auction=<%=auctions.get(i).getID()%>">
						<font size="8"><b><%=auctions.get(i).getName()%></b></font><br></a>
					<%=auctions.get(i).getDescription()%></td>
				<td><b>Current Price: </b><font color="red"><%=auctions.get(i).getCurrentPrice()%></font><br>
				    <b>Start &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Price: </b><%=auctions.get(i).getStartPrice()%><br>
				    <b>Buy Out Price: </b><%=auctions.get(i).getBuyOut()%>
				</td>
				<td><b>Finish&nbsp; Time: </b><br><font color="red"><%=auctions.get(i).getAuctionTime()%></font><br>
				    <b>Start&nbsp;&nbsp; Time: </b><br><%=auctions.get(i).getStartTime()%><br>
				    <b>Minutes Left: </b><%=((auctions.get(i).getAuctionTime().getTime()-System.currentTimeMillis())/60000)%>
				</td>
				<td><h2><%=auctions.get(i).getUserName()%></h2></td>
				<td><h2><b><%=auctions.get(i).getWinner()%></b></h2></td>
				<% if (auctions.get(i).getSold()==0)
					message="<font color=\"red\"><h2>Sold Out</h2></font>";
					else if(auctions.get(i).getSold()==1)
						message="<font color=\"blue\"><h2>Available</h2></font>";
						else
							message="Failed";
					%>
				<td><%=message%></td>
			</tr>
			<%
				}
						}
					}
				}
			%>
		</table>
		<%
			//auctionDB.add("bob", "bob", 0, 0, 0, new Time(0), "bob");
			//auctionDB.deleteTable();
			auctionDB.closeAuctionDB();
		%>



	</div>
</body>
</html>
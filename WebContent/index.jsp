<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,model.AuctionDB,model.Auction,java.util.*"
	errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Aucion</title>
<link rel="stylesheet" href="style.css">
</head>

<body class="twoColLiqLtHdr" background="images\bidground.jpg" style="background-repeat:no-repeat" >
	<div id="container">
		<div id="header">
			<h1>Auction System</h1>
		</div>
		<div id="sidebar1">
			<div id="login">
				<%
					if (session.getAttribute("name") == null) {
				%>
				<a href="login">Login</a><br> <a href="register">register</a> <%
 	} else {
 %> Welcome back, <%=session.getAttribute("name")%> <br> <a
						href="logout">Log out</a>
				<br> <%
 	}
 %>
			</div>
			<div id="menu">
				<%
					if (session.getAttribute("name") == null) {
				%>
					<br/><br/><br/><p><h3>Welcome Guest!</h3><br/><br/>
					To use more function of this auction system, you need to register.
					</p>
				<%
					} else {
				%><br/><br/>
				<ul>
				<li><a href="addNewAuction">Create a new Auction</a></li><hr/>
				<li><a href="index.jsp?pages=display">Display Current Auctions</a></li>			
				<hr/>
				<li><a href="index.jsp?pages=currentAuction">Check My Current Auction</a></li>
				<li><a href="index.jsp?pages=pastAuction">Check My Success Auction</a></li>
				<li><a href="index.jsp?pages=allAuction">Check ALL My Auctions</a></li>
				<hr/>
				<li><a href="index.jsp?pages=winner">Winner Auction I Got</a></li>				
				</ul>
				<%
					}
				%>

			</div>

		</div>

		<div id="mainContent">
		<jsp:include page="pages/display.jsp" flush="true"/>
			
		</div>
		<br class="clearfloat" />
		<div id="footer">
			<p></p>
		</div>
	</div>
</body>
</html>

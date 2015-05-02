package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

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
import java.sql.Timestamp;
import java.util.Collection;
import model.Auction;

public class AuctionDB {

	private static final long serialVersionUID = 1L;
	private String framework = "derbyclient";
	private String driver = "org.apache.derby.jdbc.ClientDriver";
	private String protocol = "jdbc:derby://localhost:1527/";
	Connection conn = null;
	Properties props = new Properties();
	Statement s = null;
	ResultSet rs = null;
	static String dbName = "auction";
	static String tableName = "auction";
	PreparedStatement psInsert = null;
	PreparedStatement psDelete = null;
	PreparedStatement psUpdate = null;

	public AuctionDB() {

	}

	public boolean connectAcutionDB() {
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
			connectTable();
			setTools();
		} catch (SQLException sqle) {
			return false;
		}
		return true;
	}

	public boolean closeAuctionDB() {
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
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void setProperties() {
		props.put("user", "admin");
		props.put("password", "123456");
	}

	private void createDB() throws SQLException {
		conn = DriverManager.getConnection(protocol + dbName + ";create=true",
				props);
	}

	public int getMaxID() throws SQLException {
		s = conn.createStatement();
		rs = s.executeQuery("select max(ID) from " + tableName);
		if (!rs.next()) {
			return 0; // database empty
		}
		return rs.getInt("1");
	}

	public void updateWinner(String value, int id) throws SQLException {
		try {
			if (value == null || value == "")
				return;
			psUpdate = conn.prepareStatement("update " + tableName
					+ " set Winner = ? WHERE ID = ?");
			psUpdate.setString(1, value);
			psUpdate.setInt(2, id);
			psUpdate.executeUpdate();
			conn.commit();
			psUpdate.close();
			return;
		} catch (NumberFormatException e) {
			System.out.println("Wrong input type, please input again");
		}
	}

	public void updateTime(Timestamp value, int id) throws SQLException {
		try {
			if (value == null)
				return;
			psUpdate = conn.prepareStatement("update " + tableName
					+ " set AUCTIONTIME = ? WHERE ID = ?");
			psUpdate.setTimestamp(1, value);
			psUpdate.setInt(2, id);
			psUpdate.executeUpdate();
			conn.commit();
			psUpdate.close();
			return;
		} catch (NumberFormatException e) {
			System.out.println("Wrong input type, please input again");
		}
	}

	public void updateCurrentPrice(int value, int id) throws SQLException {
		try {
			if (value <= 0)
				return;
			psUpdate = conn.prepareStatement("update " + tableName
					+ " set CURRENTPRICE = ? WHERE ID = ?");
			psUpdate.setInt(1, value);
			psUpdate.setInt(2, id);
			psUpdate.executeUpdate();
			conn.commit();
			psUpdate.close();
			return;
		} catch (NumberFormatException e) {
			System.out.println("Wrong input type, please input again");
		}
	}

	public void updateSold(int value, int id) throws SQLException {
		try {
			if (value < 0)
				return;
			psUpdate = conn.prepareStatement("update " + tableName
					+ " set SOLD = ? WHERE ID = ?");
			psUpdate.setInt(1, value);
			psUpdate.setInt(2, id);
			psUpdate.executeUpdate();
			conn.commit();
			psUpdate.close();
			return;
		} catch (NumberFormatException e) {
			System.out.println("Wrong input type, please input again");
		}
	}

	public void add(String name, String description, int reserver, int buyOut,
			int startPrice, Timestamp auctionTime, String username) {

		try {
			if (name.equals(null))
				return;
			psInsert = conn.prepareStatement("insert into " + tableName
					+ " values (?, ?,?,?,?,?,?,?,?,?,?,?)");
			psInsert.setInt(1, getMaxID() + 1);
			psInsert.setString(2, name);
			psInsert.setString(3, description);
			psInsert.setInt(4, reserver);
			psInsert.setInt(5, buyOut);
			psInsert.setInt(6, startPrice);
			psInsert.setInt(7, startPrice);
			psInsert.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			psInsert.setTimestamp(9, auctionTime);
			psInsert.setString(10, "");
			psInsert.setString(11, username);
			psInsert.setInt(12, 1);// 1 for not sale
			psInsert.executeUpdate();
			conn.commit();
			psInsert.close();
			return;
		} catch (NumberFormatException e) {
			System.out.println("Wrong input type, please input again");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteTable() {
		try {
			s = conn.createStatement();
			s.execute("drop table " + tableName);
			conn.commit();
		} catch (NumberFormatException e) {
			System.out.println("Wrong input type, please input again");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Timestamp getAuctionTime(int id) {
		try {
			s = conn.createStatement();
			rs = s.executeQuery("select * from " + tableName + " WHERE ID= "
					+ id);
			if (!rs.next())
				return null;
			return rs.getTimestamp("AUCTIONTIME");
		} catch (Exception e) {
			return null;
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
		if (set.contains("auction".toUpperCase())) {

		} else {
			s = conn.createStatement();
			s.execute("create table " + tableName
					+ " (ID int NOT NULL UNIQUE, "
					+ "NAME varchar(20) NOT NULL, "
					+ "DESCRIPTION varchar(100), " + "RESERVER int, "
					+ "BUYOUT int, " + "STARTINGPRICE int,"
					+ "CURRENTPRICE int," + "STARTTIME TIMESTAMP NOT NULL,"
					+ "AUCTIONTIME TIMESTAMP NOT NULL," + "WINNER varchar(20),"
					+ "USERNAME varchar(20) NOT NULL, " + "SOLD int NOT NULL)");
		}
		conn.commit();
	}

	private void setTools() throws SQLException {

	}

	public int getMaxNumber() {
		try {
			s = conn.createStatement();
			rs = s.executeQuery("select * from " + tableName);
			if (!rs.next())
				return 0;
			rs.last();
			return rs.getRow();
		} catch (Exception e) {
			return 0;
		}
	}

	public ArrayList<Auction> searchWinner(String username) {
		try {
			ArrayList<Auction> auctions = new ArrayList<Auction>();
			String[] keywords = username.split(" ");
			String sql = "select * from " + tableName + " WHERE ";
			for (int i = 0; i < keywords.length; i++) {
				if (i != 0)
					sql = sql + "OR ";
				sql = sql + "WINNER = " + "'" + keywords[i] + "'";
			}
			s = conn.createStatement();
			System.out.println(sql);
			rs = s.executeQuery(sql);
			if (!rs.next())
				return null;
			do {
				Auction auction = new Auction();
				auction.setID(rs.getInt("ID"));
				auction.setName(rs.getString("NAME"));
				auction.setDescription(rs.getString("DESCRIPTION"));
				auction.setReserver(rs.getInt("RESERVER"));
				auction.setBuyOut(rs.getInt("BUYOUT"));
				auction.setStartPrice(rs.getInt("STARTINGPRICE"));
				auction.setCurrentPrice(rs.getInt("CURRENTPRICE"));
				auction.setStartTime(rs.getTimestamp("STARTTIME"));
				auction.setAuctionTime(rs.getTimestamp("AUCTIONTIME"));
				auction.setUserName(rs.getString("USERNAME"));
				auction.setWinner(rs.getString("WINNER"));
				auction.setSold(rs.getInt("SOLD"));
				auctions.add(auction);
			} while (rs.next());
			return auctions;
		} catch (Exception e) {
			System.out.print("error SQL");
			return null;
		}
	}

	public ArrayList<Auction> searchWinnerByKeyword(String keyword,
			String winner) {
		try {
			ArrayList<Auction> auctions = new ArrayList<Auction>();
			String[] keywords = keyword.split(" ");
			String[] usernames = winner.split(" ");
			String sql = "select * from (select * from " + tableName
					+ " where ";
			for (int i = 0; i < usernames.length; i++) {
				if (i != 0)
					sql = sql + "OR ";
				sql = sql + "WINNER = " + "'" + usernames[i] + "' ";
			}
			sql = sql + ") as t WHERE ";
			for (int i = 0; i < keywords.length; i++) {
				if (i != 0)
					sql = sql + "OR ";
				sql = sql + "NAME LIKE " + "'% " + keywords[i] + " %' OR ";
				sql = sql + "NAME LIKE " + "'" + keywords[i] + " %' OR ";
				sql = sql + "NAME LIKE " + "'% " + keywords[i] + "' OR ";
				sql = sql + "NAME LIKE " + "'% " + keywords[i] + " %' OR ";
				sql = sql + "NAME LIKE " + "'" + keywords[i] + "' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'% " + keywords[i]
						+ " %' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'" + keywords[i] + " %' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'% " + keywords[i] + "' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'% " + keywords[i]
						+ "_ %' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'" + keywords[i] + "' ";
			}
			s = conn.createStatement();
			System.out.println(sql);
			rs = s.executeQuery(sql);
			if (!rs.next())
				return null;
			do {
				Auction auction = new Auction();
				auction.setID(rs.getInt("ID"));
				auction.setName(rs.getString("NAME"));
				auction.setDescription(rs.getString("DESCRIPTION"));
				auction.setReserver(rs.getInt("RESERVER"));
				auction.setBuyOut(rs.getInt("BUYOUT"));
				auction.setStartPrice(rs.getInt("STARTINGPRICE"));
				auction.setCurrentPrice(rs.getInt("CURRENTPRICE"));
				auction.setStartTime(rs.getTimestamp("STARTTIME"));
				auction.setAuctionTime(rs.getTimestamp("AUCTIONTIME"));
				auction.setUserName(rs.getString("USERNAME"));
				auction.setWinner(rs.getString("WINNER"));
				auction.setSold(rs.getInt("SOLD"));
				auctions.add(auction);
			} while (rs.next());
			return auctions;
		} catch (Exception e) {
			System.out.print("error SQL");
			return null;
		}
	}

	public ArrayList<Auction> searchWinnerByUsername(String username,
			String winner) {
		try {
			ArrayList<Auction> auctions = new ArrayList<Auction>();
			String[] keywords = username.split(" ");
			String[] usernames = winner.split(" ");
			String sql = "select * from (select * from " + tableName
					+ " where ";
			for (int i = 0; i < usernames.length; i++) {
				if (i != 0)
					sql = sql + "OR ";
				sql = sql + "WINNER = " + "'" + usernames[i] + "' ";
			}
			sql = sql + ") as t WHERE ";
			for (int i = 0; i < keywords.length; i++) {
				if (i != 0)
					sql = sql + "OR ";
				sql = sql + "USERNAME LIKE " + "'% " + keywords[i] + " %' OR ";
				sql = sql + "USERNAME LIKE " + "'" + keywords[i] + " %' OR ";
				sql = sql + "USERNAME LIKE " + "'% " + keywords[i] + "' OR ";
				sql = sql + "USERNAME LIKE " + "'% " + keywords[i] + " %' OR ";
				sql = sql + "USERNAME LIKE " + "'" + keywords[i] + "' ";
			}
			s = conn.createStatement();
			System.out.println(sql);
			rs = s.executeQuery(sql);
			if (!rs.next())
				return null;
			do {
				Auction auction = new Auction();
				auction.setID(rs.getInt("ID"));
				auction.setName(rs.getString("NAME"));
				auction.setDescription(rs.getString("DESCRIPTION"));
				auction.setReserver(rs.getInt("RESERVER"));
				auction.setBuyOut(rs.getInt("BUYOUT"));
				auction.setStartPrice(rs.getInt("STARTINGPRICE"));
				auction.setCurrentPrice(rs.getInt("CURRENTPRICE"));
				auction.setStartTime(rs.getTimestamp("STARTTIME"));
				auction.setAuctionTime(rs.getTimestamp("AUCTIONTIME"));
				auction.setUserName(rs.getString("USERNAME"));
				auction.setWinner(rs.getString("WINNER"));
				auction.setSold(rs.getInt("SOLD"));
				auctions.add(auction);
			} while (rs.next());
			return auctions;
		} catch (Exception e) {
			System.out.print("error SQL");
			return null;
		}
	}

	public ArrayList<Auction> searchWinnerByBoth(String keyword,
			String username, String winner) {
		try {
			ArrayList<Auction> auctions = new ArrayList<Auction>();
			String[] keywords = keyword.split(" ");
			String[] usernames = username.split(" ");
			String[] winners = winner.split(" ");
			String sql = "select * from (select * from (select * from " + tableName
					+ " where ";
			for (int i = 0; i < usernames.length; i++) {
				if (i != 0)
					sql = sql + "OR ";
				sql = sql + "WINNER = " + "'" + winners[i] + "' ";
			}
			sql = sql + ") as t WHERE ";
			for (int i = 0; i < usernames.length; i++) {
				if (i != 0)
					sql = sql + "OR ";
				sql = sql + "USERNAME LIKE " + "'% " + usernames[i] + " %' OR ";
				sql = sql + "USERNAME LIKE " + "'" + usernames[i] + " %' OR ";
				sql = sql + "USERNAME LIKE " + "'% " + usernames[i] + "' OR ";
				sql = sql + "USERNAME LIKE " + "'% " + usernames[i] + " %' OR ";
				sql = sql + "USERNAME LIKE " + "'" + usernames[i] + "' ";
			}
			sql = sql + ") as q WHERE ";
			for (int i = 0; i < keywords.length; i++) {
				if (i != 0)
					sql = sql + "OR ";
				sql = sql + "NAME LIKE " + "'% " + keywords[i] + " %' OR ";
				sql = sql + "NAME LIKE " + "'" + keywords[i] + " %' OR ";
				sql = sql + "NAME LIKE " + "'% " + keywords[i] + "' OR ";
				sql = sql + "NAME LIKE " + "'% " + keywords[i] + " %' OR ";
				sql = sql + "NAME LIKE " + "'" + keywords[i] + "' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'% " + keywords[i]
						+ " %' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'" + keywords[i] + " %' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'% " + keywords[i] + "' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'% " + keywords[i]
						+ "_ %' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'" + keywords[i] + "' ";
			}
			s = conn.createStatement();
			System.out.println(sql);
			rs = s.executeQuery(sql);
			if (!rs.next())
				return null;
			do {
				Auction auction = new Auction();
				auction.setID(rs.getInt("ID"));
				auction.setName(rs.getString("NAME"));
				auction.setDescription(rs.getString("DESCRIPTION"));
				auction.setReserver(rs.getInt("RESERVER"));
				auction.setBuyOut(rs.getInt("BUYOUT"));
				auction.setStartPrice(rs.getInt("STARTINGPRICE"));
				auction.setCurrentPrice(rs.getInt("CURRENTPRICE"));
				auction.setStartTime(rs.getTimestamp("STARTTIME"));
				auction.setAuctionTime(rs.getTimestamp("AUCTIONTIME"));
				auction.setUserName(rs.getString("USERNAME"));
				auction.setWinner(rs.getString("WINNER"));
				auction.setSold(rs.getInt("SOLD"));
				auctions.add(auction);
			} while (rs.next());
			return auctions;
		} catch (Exception e) {
			System.out.print("error SQL");
			return null;
		}
	}

	public ArrayList<Auction> searchKeyword(String keyword) {
		try {
			ArrayList<Auction> auctions = new ArrayList<Auction>();
			String[] keywords = keyword.split(" ");
			String sql = "select * from " + tableName + " WHERE ";
			for (int i = 0; i < keywords.length; i++) {
				if (i != 0)
					sql = sql + "OR ";
				sql = sql + "NAME LIKE " + "'% " + keywords[i] + " %' OR ";
				sql = sql + "NAME LIKE " + "'" + keywords[i] + " %' OR ";
				sql = sql + "NAME LIKE " + "'% " + keywords[i] + "' OR ";
				sql = sql + "NAME LIKE " + "'% " + keywords[i] + " %' OR ";
				sql = sql + "NAME LIKE " + "'" + keywords[i] + "' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'% " + keywords[i]
						+ " %' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'" + keywords[i] + " %' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'% " + keywords[i] + "' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'% " + keywords[i]
						+ "_ %' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'" + keywords[i] + "' ";
			}
			s = conn.createStatement();
			System.out.println(sql);
			rs = s.executeQuery(sql);
			if (!rs.next())
				return null;
			do {
				Auction auction = new Auction();
				auction.setID(rs.getInt("ID"));
				auction.setName(rs.getString("NAME"));
				auction.setDescription(rs.getString("DESCRIPTION"));
				auction.setReserver(rs.getInt("RESERVER"));
				auction.setBuyOut(rs.getInt("BUYOUT"));
				auction.setStartPrice(rs.getInt("STARTINGPRICE"));
				auction.setCurrentPrice(rs.getInt("CURRENTPRICE"));
				auction.setStartTime(rs.getTimestamp("STARTTIME"));
				auction.setAuctionTime(rs.getTimestamp("AUCTIONTIME"));
				auction.setUserName(rs.getString("USERNAME"));
				auction.setWinner(rs.getString("WINNER"));
				auction.setSold(rs.getInt("SOLD"));
				auctions.add(auction);
			} while (rs.next());
			return auctions;
		} catch (Exception e) {
			System.out.print("error SQL");
			return null;
		}
	}

	public ArrayList<Auction> searchUser(String username) {
		try {
			ArrayList<Auction> auctions = new ArrayList<Auction>();
			String[] keywords = username.split(" ");
			String sql = "select * from " + tableName + " WHERE ";
			for (int i = 0; i < keywords.length; i++) {
				if (i != 0)
					sql = sql + "OR ";
				sql = sql + "USERNAME = " + "'" + keywords[i] + "'";
			}
			s = conn.createStatement();
			System.out.println(sql);
			rs = s.executeQuery(sql);
			if (!rs.next())
				return null;
			do {
				Auction auction = new Auction();
				auction.setID(rs.getInt("ID"));
				auction.setName(rs.getString("NAME"));
				auction.setDescription(rs.getString("DESCRIPTION"));
				auction.setReserver(rs.getInt("RESERVER"));
				auction.setBuyOut(rs.getInt("BUYOUT"));
				auction.setStartPrice(rs.getInt("STARTINGPRICE"));
				auction.setCurrentPrice(rs.getInt("CURRENTPRICE"));
				auction.setStartTime(rs.getTimestamp("STARTTIME"));
				auction.setAuctionTime(rs.getTimestamp("AUCTIONTIME"));
				auction.setUserName(rs.getString("USERNAME"));
				auction.setWinner(rs.getString("WINNER"));
				auction.setSold(rs.getInt("SOLD"));
				auctions.add(auction);
			} while (rs.next());
			return auctions;
		} catch (Exception e) {
			System.out.print("error SQL");
			return null;
		}
	}

	public ArrayList<Auction> searchKeywordByUser(String keyword,
			String username) {
		try {
			ArrayList<Auction> auctions = new ArrayList<Auction>();
			String[] keywords = keyword.split(" ");
			String[] usernames = username.split(" ");
			String sql = "select * from (select * from " + tableName
					+ " where ";

			for (int i = 0; i < usernames.length; i++) {
				if (i != 0)
					sql = sql + "OR ";
				sql = sql + "USERNAME = " + "'" + usernames[i] + "' OR ";
				sql = sql + "USERNAME LIKE " + "'%" + usernames[i] + "%' ";

			}
			sql = sql + ") as t WHERE ";
			for (int i = 0; i < keywords.length; i++) {
				if (i != 0)
					sql = sql + "OR ";
				sql = sql + "NAME LIKE " + "'% " + keywords[i] + " %' OR ";
				sql = sql + "NAME LIKE " + "'" + keywords[i] + " %' OR ";
				sql = sql + "NAME LIKE " + "'% " + keywords[i] + "' OR ";
				sql = sql + "NAME LIKE " + "'% " + keywords[i] + " %' OR ";
				sql = sql + "NAME LIKE " + "'" + keywords[i] + "' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'% " + keywords[i]
						+ " %' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'" + keywords[i] + " %' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'% " + keywords[i] + "' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'% " + keywords[i]
						+ "_ %' OR ";
				sql = sql + "DESCRIPTION LIKE " + "'" + keywords[i] + "' ";
			}
			s = conn.createStatement();
			System.out.println(sql);
			rs = s.executeQuery(sql);
			if (!rs.next())
				return null;
			do {
				Auction auction = new Auction();
				auction.setID(rs.getInt("ID"));
				auction.setName(rs.getString("NAME"));
				auction.setDescription(rs.getString("DESCRIPTION"));
				auction.setReserver(rs.getInt("RESERVER"));
				auction.setBuyOut(rs.getInt("BUYOUT"));
				auction.setStartPrice(rs.getInt("STARTINGPRICE"));
				auction.setCurrentPrice(rs.getInt("CURRENTPRICE"));
				auction.setStartTime(rs.getTimestamp("STARTTIME"));
				auction.setAuctionTime(rs.getTimestamp("AUCTIONTIME"));
				auction.setUserName(rs.getString("USERNAME"));
				auction.setWinner(rs.getString("WINNER"));
				auction.setSold(rs.getInt("SOLD"));
				auctions.add(auction);
			} while (rs.next());
			return auctions;
		} catch (Exception e) {
			System.out.print("error SQL");
			return null;
		}
	}

	public ArrayList<Auction> checkAuctionDB() {
		try {
			ArrayList<Auction> auctions = new ArrayList<Auction>();
			s = conn.createStatement();
			rs = s.executeQuery("select * from " + tableName
					+ " WHERE SOLD = 1 ORDER BY ID");
			if (!rs.next())
				return null;
			do {
				Auction auction = new Auction();
				auction.setID(rs.getInt("ID"));
				auction.setName(rs.getString("NAME"));
				auction.setDescription(rs.getString("DESCRIPTION"));
				auction.setReserver(rs.getInt("RESERVER"));
				auction.setBuyOut(rs.getInt("BUYOUT"));
				auction.setStartPrice(rs.getInt("STARTINGPRICE"));
				auction.setCurrentPrice(rs.getInt("CURRENTPRICE"));
				auction.setStartTime(rs.getTimestamp("STARTTIME"));
				auction.setAuctionTime(rs.getTimestamp("AUCTIONTIME"));
				auction.setUserName(rs.getString("USERNAME"));
				auction.setWinner(rs.getString("WINNER"));
				auction.setSold(rs.getInt("SOLD"));
				auctions.add(auction);
			} while (rs.next());
			return auctions;
		} catch (Exception e) {
			System.out.print(e);
			return null;
		}
	}

	public ArrayList<Auction> displayByID() {
		try {
			ArrayList<Auction> auctions = new ArrayList<Auction>();
			s = conn.createStatement();

			rs = s.executeQuery("select * from " + tableName + " ORDER BY ID");
			if (!rs.next())
				return null;
			do {
				Auction auction = new Auction();
				auction.setID(rs.getInt("ID"));
				auction.setName(rs.getString("NAME"));
				auction.setDescription(rs.getString("DESCRIPTION"));
				auction.setReserver(rs.getInt("RESERVER"));
				auction.setBuyOut(rs.getInt("BUYOUT"));
				auction.setStartPrice(rs.getInt("STARTINGPRICE"));
				auction.setCurrentPrice(rs.getInt("CURRENTPRICE"));
				auction.setStartTime(rs.getTimestamp("STARTTIME"));
				auction.setAuctionTime(rs.getTimestamp("AUCTIONTIME"));
				auction.setUserName(rs.getString("USERNAME"));
				auction.setWinner(rs.getString("WINNER"));
				auction.setSold(rs.getInt("SOLD"));
				auctions.add(auction);
			} while (rs.next());
			return auctions;
		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<Auction> topAuction() {
		try {
			ArrayList<Auction> auctions = new ArrayList<Auction>();
			s = conn.createStatement();
			rs = s.executeQuery("select * from auction where SOLD = 1 order by AUCTIONTIME desc FETCH FIRST 10 ROWS ONLY");
			if (!rs.next()) {
				return null;
			}
			do {
				Auction auction = new Auction();
				auction.setID(rs.getInt("ID"));
				auction.setName(rs.getString("NAME"));
				auction.setDescription(rs.getString("DESCRIPTION"));
				auction.setReserver(rs.getInt("RESERVER"));
				auction.setBuyOut(rs.getInt("BUYOUT"));
				auction.setStartPrice(rs.getInt("STARTINGPRICE"));
				auction.setCurrentPrice(rs.getInt("CURRENTPRICE"));
				auction.setStartTime(rs.getTimestamp("STARTTIME"));
				auction.setAuctionTime(rs.getTimestamp("AUCTIONTIME"));
				auction.setUserName(rs.getString("USERNAME"));
				auction.setWinner(rs.getString("WINNER"));
				auction.setSold(rs.getInt("SOLD"));
				auctions.add(auction);
			} while (rs.next());
			return auctions;
		} catch (Exception e) {
			return null;
		}
	}

	public Auction getAuctionByID(int id) {
		try {
			Auction auction = new Auction();
			s = conn.createStatement();
			rs = s.executeQuery("select * from " + tableName + " WHERE ID= "
					+ id);
			if (!rs.next())
				return null;
			auction.setID(rs.getInt("ID"));
			auction.setName(rs.getString("NAME"));
			auction.setDescription(rs.getString("DESCRIPTION"));
			auction.setReserver(rs.getInt("RESERVER"));
			auction.setBuyOut(rs.getInt("BUYOUT"));
			auction.setStartPrice(rs.getInt("STARTINGPRICE"));
			auction.setCurrentPrice(rs.getInt("CURRENTPRICE"));
			auction.setStartTime(rs.getTimestamp("STARTTIME"));
			auction.setAuctionTime(rs.getTimestamp("AUCTIONTIME"));
			auction.setUserName(rs.getString("USERNAME"));
			auction.setWinner(rs.getString("WINNER"));
			auction.setSold(rs.getInt("SOLD"));
			return auction;
		} catch (Exception e) {
			return null;
		}
	}

}

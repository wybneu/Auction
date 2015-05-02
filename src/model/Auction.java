package model;
import java.sql.Timestamp;
public class Auction {
	int ID;
	String name;
	String description;
	int reserver;
	int buyOut;
	int startPrice;
	int currentPrice;
	Timestamp startTime;	
	Timestamp auctionTime;
	String userName;
	String winner;
	int sold;
	public Auction(){
		
	}
	public Auction clone(){
		Auction temp=new Auction();
		temp.setID(ID);
		temp.setName(name);
		temp.setDescription(description);
		temp.setReserver(reserver);
		temp.setBuyOut(buyOut);
		temp.setStartPrice(startPrice);
		temp.setCurrentPrice(currentPrice);
		temp.setStartTime(startTime);
		temp.setAuctionTime(auctionTime);
		temp.setUserName(userName);
		temp.setWinner(winner);
		temp.setSold(sold);
		return temp;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public int getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getReserver() {
		return reserver;
	}
	public void setReserver(int reserver) {
		this.reserver = reserver;
	}
	public int getBuyOut() {
		return buyOut;
	}
	public void setBuyOut(int buyOut) {
		this.buyOut = buyOut;
	}
	public int getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}
	public Timestamp getAuctionTime() {
		return auctionTime;
	}
	public void setAuctionTime(Timestamp auctionTime) {
		this.auctionTime = auctionTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getSold() {
		return sold;
	}
	public void setSold(int sold) {
		this.sold = sold;
	}
}

package controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import model.*;

public class Monitor {
    AuctionDB auctionDB;
    ArrayList<Auction> auctions;
	 
	 public void verify() {
		 auctionDB = new AuctionDB();
		 auctionDB.connectAcutionDB();
		verifyDB();
		auctionDB.closeAuctionDB();
	}
	 
	 private void verifyDB(){
		
		 auctions=auctionDB.checkAuctionDB();
		 if (auctions==null||auctions.size()==0) return;
		 for(int i=0;i<auctions.size();i++){
			 if(!auctions.get(i).getAuctionTime().after(new Timestamp(System.currentTimeMillis()))){
				 try{
					 if(auctions.get(i).getCurrentPrice()>auctions.get(i).getReserver()){
						
						 auctionDB.updateSold(0, auctions.get(i).getID());
						 System.out.println(auctions.get(i).getID()+" " +auctions.get(i).getName() +" has been sold");
					 }else{
						 auctionDB.updateSold(2, auctions.get(i).getID());
						 System.out.println(auctions.get(i).getID()+" " +auctions.get(i).getName() +" Failed");
					 }
				 }catch(Exception e){
					 System.out.print(e);
				 }
			 };
		 }
	 }
}

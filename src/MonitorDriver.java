import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import model.*;

public class MonitorDriver extends Thread{
    final Timer fresh = new Timer();
    TimerTask timerTask;
    private int timerSecond=10000;
    AuctionDB auctionDB = new AuctionDB();;
    ArrayList<Auction> auctions;
    
	public void run() {
		System.out.println("Monitor is Started");
		fresh.schedule(timerTask = new TimerTask() {
	         public void run() {
	             refresh();
	         }			
	     }, 1000, timerSecond);
	 }
	 
	 private void refresh() {
		 auctionDB.connectAcutionDB();
		verifyDB();
		auctionDB.closeAuctionDB();
	}
	 
	 private void verifyDB(){
		 auctions=auctionDB.topAuction();
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
	 public static void main(String[] args){
		 MonitorDriver monitorDriver=new MonitorDriver();
		 monitorDriver.start();
	 }
}

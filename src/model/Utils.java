package model;

import java.util.*;
import model.Auction;

public class Utils {
    public ArrayList<Auction> getAuctionByAsctime(ArrayList<Auction> auction) {
    	if (auction==null||auction.size()==0) return null;
        ArrayList<Auction> temp = auction;
        Auction auctionTemp = new Auction();
        for (int i = 0; i < temp.size() - 1; i++) {
            for (int j = 1; j < temp.size() - i; j++) {
                if (temp.get(j - 1).getAuctionTime()
                        .compareTo(temp.get(j).getAuctionTime()) > 0) {
                    auctionTemp = temp.get(j - 1).clone();
                    temp.set(j - 1, temp.get(j).clone());
                    temp.set(j, auctionTemp);
                }
            }
        }
        return temp;
    }

    public ArrayList<Auction> getAuctionByDesctime(ArrayList<Auction> auction) {
    	if (auction==null||auction.size()==0) return null;
        ArrayList<Auction> temp = auction;
        Auction auctionTemp = new Auction();
        for (int i = 0; i < temp.size() - 1; i++) {
            for (int j = 1; j < temp.size() - i; j++) {
                if (temp.get(j - 1).getAuctionTime()
                        .compareTo(temp.get(j).getAuctionTime()) < 0) {
                    auctionTemp = temp.get(j - 1).clone();
                    temp.set(j - 1, temp.get(j).clone());
                    temp.set(j, auctionTemp);
                }
            }
        }
        return temp;
    }

    public ArrayList<Auction> getAuctionByAscbid(ArrayList<Auction> auction) {
    	if (auction==null||auction.size()==0) return null;
        ArrayList<Auction> temp = auction;
        Auction auctionTemp = new Auction();
        for (int i = 0; i < temp.size() - 1; i++) {
            for (int j = 1; j < temp.size() - i; j++) {
                if (temp.get(j - 1).getCurrentPrice() > temp.get(j)
                        .getCurrentPrice()) {
                    auctionTemp = temp.get(j - 1).clone();
                    temp.set(j - 1, temp.get(j).clone());
                    temp.set(j, auctionTemp);
                }
            }
        }
        return temp;
    }

    public ArrayList<Auction> getAuctionByDescbid(ArrayList<Auction> auction) {
    	if (auction==null||auction.size()==0) return null;
        ArrayList<Auction> temp = auction;
        Auction auctionTemp = new Auction();
        for (int i = 0; i < temp.size() - 1; i++) {
            for (int j = 1; j < temp.size() - i; j++) {
                if (temp.get(j - 1).getCurrentPrice() < temp.get(j)
                        .getCurrentPrice()) {
                    auctionTemp = temp.get(j - 1).clone();
                    temp.set(j - 1, temp.get(j).clone());
                    temp.set(j, auctionTemp);
                }
            }
        }
        return temp;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctionDatas;

import java.util.ArrayList;


/**
 * This Class is responsible to provide data about Auction and Product Register.
 * @author Jessica
 */
public class Product {
    public  String produtcName;
    public  Integer productID;
    public  Float initialPrice;
    public  String Description;
    public  Integer timeOfAuction;
    public  Float newPrice;
    public  String onwer;
    public  ArrayList<String> Interestedbuyers;
    public  Long auctionInitialTime;
    public  Long auctionEndTime;
    public  String winner;

    public Product(String produtcName, Integer productID, Float initialPrice, String Description, Integer timeOfAuction, String onwer) {
        this.produtcName = produtcName;
        this.productID = productID;
        this.initialPrice = initialPrice;
        this.Description = Description;
        this.timeOfAuction = timeOfAuction;
        this.onwer = onwer;
    }

    public String getProdutcName() {
        return produtcName;
    }

    public void setProdutcName(String produtcName) {
        this.produtcName = produtcName;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Float getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Float initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Integer getTimeOfAuction() {
        return timeOfAuction;
    }

    public void setTimeOfAuction(Integer timeOfAuction) {
        this.timeOfAuction = timeOfAuction;
    }

    public Float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Float newPrice) {
        this.newPrice = newPrice;
    }

    public String getOnwer() {
        return onwer;
    }

    public void setOnwer(String onwer) {
        this.onwer = onwer;
    }

    public ArrayList<String> getInterestedbuyers() {
        return Interestedbuyers;
    }

    public void setInterestedbuyers(ArrayList<String> Interestedbuyers) {
        this.Interestedbuyers = Interestedbuyers;
    }

    public Long getAuctionInitialTime() {
        return auctionInitialTime;
    }

    public void setAuctionInitialTime(Long auctionInitialTime) {
        this.auctionInitialTime = auctionInitialTime;
    }

    public Long getAuctionEndTime() {
        return auctionEndTime;
    }

    public void setAuctionEndTime(Long auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

 

    
}

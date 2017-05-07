package auction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author allan
 */
public interface InterfaceServ extends Remote  {
    
    public void chamar(String nomeCliente, InterfaceCli refCliente) throws RemoteException;
    public  void auctionRegister(Integer productID, String productName,
             Float initialPrice, String description, Integer timeOfAuction,
             String onwer) throws RemoteException;
    public void generateBid(String buyer, Float bidPrice, Integer productID)throws RemoteException;
    //public void activeAuctionQuery(Integer productID, )throws RemoteException;
    public void auctionEnds();
}

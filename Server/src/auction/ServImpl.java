/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction;


import auctionDatas.Product;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

/**
 *
 * @author allan
 */
public class ServImpl extends UnicastRemoteObject  implements InterfaceServ{

    // List of possible buyers;
       
    // List of auctions;
    public Map<Integer, Product> activeAuctions;
    
    public ServImpl() throws RemoteException {
    }


    @Override
    public void chamar(String nomeCliente, InterfaceCli refCliente) throws RemoteException{
        refCliente.echo(nomeCliente + "acabou de chamar o servidor");
       
    }
   
    @Override
     public void auctionRegister(Integer productID, String productName,
             Float initialPrice, String description, Integer timeOfAuction,
             String onwer) throws RemoteException{
       Product product;
       product =  new Product(productName, productID, initialPrice, description,
               timeOfAuction, onwer);     
   }

    @Override
    public void generateBid(String buyer, Float bidPrice, Integer productID) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void auctionEnds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
     
}

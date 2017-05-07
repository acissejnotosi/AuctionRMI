/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction;

import auctionDatas.Product;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Jessica
 */
public class Servidor {

    /**
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws RemoteException {
        
       toInitializeNameService();
        
        
        
    }
    
    /** Method to initialize the Name Service
     *   @return */
    
    public static void toInitializeNameService() throws RemoteException{
        ServImpl server = new ServImpl();
        int port = 6789;
        Registry referenciaServicoNomes = LocateRegistry.createRegistry(port);
        referenciaServicoNomes.rebind("Hello World", new ServImpl());
        System.out.println("Server initialize");
    }
    
  
    
    
    
}

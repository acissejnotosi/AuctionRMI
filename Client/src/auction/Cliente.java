/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Jessica
 */
public class Cliente {
 
    
     public static void main(String[] args) throws RemoteException, NotBoundException {
        // TODO code application logic here
        //Cria o registro de nomes
         int port = 6789;
        Registry referenciaServicoNomes = LocateRegistry.getRegistry(port);        
        InterfaceServ referenciaServidor = (InterfaceServ) referenciaServicoNomes.lookup("Hello World");
        CliImpl cliente = new CliImpl(referenciaServidor);
        
        System.out.println("Rodando cliente");
        
        
    }
}

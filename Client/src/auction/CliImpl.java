/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author allan
 */
public class CliImpl extends UnicastRemoteObject implements InterfaceCli{
 
public CliImpl(InterfaceServ refServidor) throws RemoteException{
   
    refServidor.chamar("Cliente Jessica", this);    
}
        

    @Override
    public void echo(String qualquer) throws RemoteException {
        
       System.out.println("Mensagem para o Servente Cliente: " + qualquer);
       
       
       
      }

   
    
    
}

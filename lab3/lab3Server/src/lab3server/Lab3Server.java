/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3server;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab3.DateTime;

public class Lab3Server {
    private final String UNIQUE_BINDING_NAME = "server.datetime";
    private static Lab3Server m_server;
    private Registry m_registry;
    
    class RemoteDateTime implements DateTime {

        @Override
        public String getDate() throws RemoteException {
            return new SimpleDateFormat("dd.mm.yyyy").format(new Date());
        }

        @Override
        public String getTime() throws RemoteException {
            return new SimpleDateFormat("hh:mm:ss").format(new Date());
        }

        @Override
        public boolean stop() throws RemoteException {
            /* Removes the remote object, obj, from the RMI runtime */
            boolean unexportResult = UnicastRemoteObject.unexportObject(m_registry, true);
            
            try {
                /* Removes the binding */
                m_registry.unbind(UNIQUE_BINDING_NAME);
            } catch (NotBoundException | AccessException ex) {
                Logger.getLogger(Lab3Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return unexportResult;
        }
    }
    
    private void startServer() throws RemoteException, AlreadyBoundException, InterruptedException, NotBoundException {
        final RemoteDateTime remoteDateTime = new RemoteDateTime();
        m_registry = LocateRegistry.createRegistry(4004);
        Remote remoteStub = UnicastRemoteObject.exportObject(remoteDateTime, 0);
        /* Binds a remote reference to the specified name in this registry */
        m_registry.bind(UNIQUE_BINDING_NAME, remoteStub);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        m_server = new Lab3Server();
        try {
            m_server.startServer();
        } catch (RemoteException | AlreadyBoundException | InterruptedException | NotBoundException ex) {
            Logger.getLogger(Lab3Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

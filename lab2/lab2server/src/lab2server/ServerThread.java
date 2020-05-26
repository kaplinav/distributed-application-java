/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2server;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab2.Registration;

public class ServerThread extends Thread {
    private Socket m_socket;
    
    public ServerThread(Socket socket) {
        m_socket = socket;
    }
   
    @Override
    public void run() {
        BufferedReader in = null;
        ObjectOutputStream objOut = null;
        
        try {
            try {
                in = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));                
                objOut = new ObjectOutputStream(m_socket.getOutputStream());
                
                /* get client login */
                String newAuthLogin = in.readLine();
                Server.addUser(new Registration(newAuthLogin));
                /* send answer to client */
                objOut.writeObject(Server.getCurrentUserDates(newAuthLogin));
                objOut.flush();

            } finally { 
                in.close();
                objOut.close();
                m_socket.close();
            } 
        
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
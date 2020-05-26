/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab2.Registration;

public class Server extends Thread{
    /* list of authenticated users */  
    private static List<Registration> m_users = new CopyOnWriteArrayList<Registration>();
    
    @Override
    public void run(){
        try (ServerSocket serverSocket = new ServerSocket(4004)) {
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                /* start new server thread */
                new ServerThread(clientSocket).start();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* get authentication dates of all users */
    public static List<Registration> getUsers() {
        return m_users;
    }
    
    /* add new authentication */
    public static void addUser(Registration user) {
        m_users.add(user);
    }
    
    /* get date of authentication of current user */
    public static List<Registration> getCurrentUserDates(String user)
    {
        List<Registration> regs = new ArrayList<>();
        m_users.forEach(item -> { 
            if (item.getUser().equals(user)) { 
                regs.add(item);        
            } 
        });
        return regs;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;

import com.sun.javafx.scene.control.skin.Utils;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;

public class Message implements Serializable{
    public static int m_last;
    private int m_id;
    private Command m_command;
    private String m_message;
    private Date m_date;
    private InetAddress m_address;
    private transient int m_port;
    
    public Message(String message, Command command) {
        m_message = message;
        m_command = command;
        m_date = new Date();
    }
    
    public Message( int id, String message, InetAddress address, int port) {
        m_id = id;
        m_message = message;
        m_date = new Date();
        m_address = address;
        m_port = port;
    }
    
    public int getId(){ return m_id; }
    public Command getCommand() { return m_command; }
    public String getMessage() { return m_message; }
    public Date getDate() { return m_date; }
    public InetAddress getAddress() { return m_address; }
    public int getPort() { return m_port; }
}
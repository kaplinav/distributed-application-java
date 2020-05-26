
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lab1_server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.objects.NativeArray;
import lab1.*;
 
public class Lab1_server {
    private static List<Message> m_messages = new ArrayList<>();
    private static final int PORT = 4445;
    
    /* return 10 last messages iff count of messages > 10 */
    /* in other case return all messages */
    private static byte[] getLastMessage(List<Message> messages){
        if (messages.isEmpty())
            return "Empty list".getBytes();
        
        int from = (messages.size() <= 10) ? 0 : messages.size() - 10;
        List<Message> subList = messages.subList(from, messages.size());
        StringBuilder s = new StringBuilder();
        
        for (Message m : subList) {
            s.append(m.getAddress() + 
                    ":" + m.getPort() + 
                    ":" + m.getId() + 
                    "-" + m.getMessage() +
                    "-" + m.getDate() +
                    System.lineSeparator());
        }
        
        return new String(s).getBytes();
    }
    
    /* get last 10 messages */
    private static byte[] getTopMessage(){
        /* a.	<address:port:id>-<message>-<date> */
        /* b.	Если полученный список пуст, то клиент отображает сообщение: <Empty list> */
        
        if (m_messages.isEmpty())
            return "Empty list".getBytes();
        
        return getLastMessage(m_messages);
    }
    
    /* get last 10 messages of current client */
    private static byte[] getClientMessage(InetAddress clientAddress){
        /* a.	<address:port:id>-<message>-<date> */
        /* b.	Если полученный список пуст, то клиент отображает сообщение: <Empty list> */
        
        if (clientAddress == null || m_messages.isEmpty() )
            return "Empty list".getBytes();
        
        List<Message> subList = new ArrayList<>();
        for (Message m : m_messages){
            if (m.getAddress().getHostAddress().equals(clientAddress.getHostAddress()))
                subList.add(m);
        }
        
        if (subList.isEmpty())
            return "Empty list".getBytes();
                
        return getLastMessage(subList);
    }
    
    /**
     * @param args the command line arguments
     * @throws java.net.SocketException
     */
    public static void main(String[] args) throws SocketException, IOException {
        DatagramSocket socket = new DatagramSocket(PORT);
        boolean work = true;
        System.out.println(" server start");
        
        while (work) {        
            /* receive package */
            byte[] buffer = new byte[1024]; 
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            
            Message m = ByteSerializer.toDeSerialize(packet.getData());
            
            if (m == null){
                System.out.println(" null");
                continue;
            }
            
            /* execute client command  */
            switch(m.getCommand()){
                /* add message to list */
                case ADD:
                    m_messages.add(
                            new Message(
                                    ++Message.m_last, 
                                    m.getMessage(), 
                                    packet.getAddress(), 
                                    packet.getPort())
                    );
                    buffer = "OK".getBytes();
                    break;
                /* get last 10 message */
                case TOP_MESSAGE:
                    buffer = getTopMessage();
                    break;
                /* get last 10 message from current client */
                case CLIENT_MESSAGE:
                    buffer = getClientMessage(packet.getAddress());
                    break;
                /* send READY */
                case PING:
                    System.out.println(" ping");
                    buffer = "READY".getBytes();
                    break;
                /* stop the server */
                case END:    
                    work = false;
                    continue;
            }
            
            /* send package */
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            socket.send(new DatagramPacket(
                    buffer, buffer.length, address, port));
        }
        socket.close();
    }
}
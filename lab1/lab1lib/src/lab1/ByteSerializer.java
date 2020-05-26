/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ByteSerializer {
    
    
    /* restore object from byte array */
    public static Message toDeSerialize(byte[] buffer){
        Message m = null;
        
        try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer)){
            try (ObjectInputStream ois = new ObjectInputStream(bis)){
                try {
                    m = (Message)ois.readObject();
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ByteSerializer.class.getName()).log(Level.SEVERE, null, ex); 
        }
        return m;
    }
    
    /* object to byte array */
    public static byte[] toSerialize(Message m) {
        byte[] buffer = null;
        
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            try (ObjectOutputStream oos = new ObjectOutputStream(bos)){
                oos.writeObject(m);
                oos.flush();
                buffer = bos.toByteArray();
            }
        } catch (IOException ex) {
            Logger.getLogger(ByteSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return buffer;
    }
}
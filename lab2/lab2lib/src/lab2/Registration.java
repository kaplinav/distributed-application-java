/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2;

import java.io.Serializable;
import java.util.Date;

public class Registration implements Serializable{
    /* date of registration */
    private Date m_date;		
    /* user identifier */
    private String m_user;

    public Registration(String user) throws Exception {
        if (user == null || user.trim().isEmpty()) {
            throw new Exception ("Invalid parameters of registration");
        }
        
        this.m_date = new Date();
        this.m_user = user;
    }
    public Date getDate() {
        return m_date;
    }

    public void setDate(Date date) {
        this.m_date = date;
    }

    public String getUser() {
        return m_user;
    }

    public void setUser(String user) {
        this.m_user = user;
    }
}

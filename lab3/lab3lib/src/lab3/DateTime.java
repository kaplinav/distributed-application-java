/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DateTime extends Remote
{
    String getDate () throws RemoteException;
    String getTime () throws RemoteException;
    boolean stop () throws RemoteException;
}


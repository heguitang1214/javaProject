package com.zero.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIDemoService extends Remote {
    String sayHello(String name) throws RemoteException;
}

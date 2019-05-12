package com.tang.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI服务注册的就是该服务
 */
public interface RMIDemoService extends Remote {
    String sayHello(String name) throws RemoteException;
}

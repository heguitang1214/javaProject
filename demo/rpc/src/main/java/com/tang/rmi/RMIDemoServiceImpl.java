package com.tang.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIDemoServiceImpl extends UnicastRemoteObject implements RMIDemoService {
    public RMIDemoServiceImpl() throws RemoteException {
    }

    @Override
    public String sayHello(String name) throws RemoteException {
        return "服务器：hello " + name;
    }
}

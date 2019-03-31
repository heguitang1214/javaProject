package designPatterns.proxy.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {

    protected MyRemoteImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hello World!";
    }

    public static void main(String[] args) {

        try {
            MyRemote service = new MyRemoteImpl();
            LocateRegistry.createRegistry(8800);
            Naming.rebind("rmi://127.0.0.1:8800/RemoteHello", service);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }


    }
}

package servidor;

import java.net.InetAddress;
import java.rmi.server.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.List;
import rmi.Result;
import rmi.ServerInterface;
import rmi.User;

public class Servidor extends UnicastRemoteObject implements ServerInterface {

    Users users;
    Logs logs;

    public Servidor() throws RemoteException {
        super();

        users = new Users();
        logs = new Logs();
    }

    public static void main(String[] args) {

        try {
            Servidor server = new Servidor();
            Registry reg = LocateRegistry.createRegistry(1099);

            reg.rebind("projeto-sd", server);

            System.out.println("IP: " + InetAddress.getLocalHost().getHostAddress());
            System.out.println("Porta: 1099");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Result login(String username, String sharedPath, List<String> files) throws RemoteException {
        return users.login(username, sharedPath, files);
    }

    @Override
    public List<User> getUsers() throws RemoteException {
        return users.getUsers();
    }

}

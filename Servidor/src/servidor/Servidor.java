package servidor;

import java.net.InetAddress;
import java.rmi.server.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.ArrayList;
import java.util.List;
import rmi.Log;
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

        Result result = login(username, sharedPath, files);

        if (result.getSuccess()) {
            logs.addLog(username + " fez Login!");
        }
        return result;
    }

    @Override
    public List<User> getUsers() throws RemoteException {
        return users.getUsers();
    }

    @Override
    public List<Log> getLogs(String username) throws RemoteException {

        User user = users.getUser(username);

        Long userTimestamp = user.getLoginTimestamp();

        ArrayList<Log> userlogs = new ArrayList();

        for (Log log : logs.getLogs()) {

            if (log.getTimestamp() >= userTimestamp) {

                userlogs.add(log);

            }

        }

        return userlogs;
    }

}

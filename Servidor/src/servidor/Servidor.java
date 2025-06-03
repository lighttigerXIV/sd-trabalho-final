package servidor;

import java.net.InetAddress;
import java.rmi.server.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.ArrayList;
import java.util.List;
import rmi.ClientInterface;
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
    public Result login(String username, String sharedPath, List<String> files, ClientInterface clientInterface) throws RemoteException {
        Result result = users.login(username, sharedPath, files, clientInterface);

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

    @Override
    public Result logout(String username) throws RemoteException {

        Result result = users.logout(username);

        if (result.getSuccess()) {
            logs.addLog(username + " Terminou a Sessao");
        }
        return result;

    }

    @Override
    public void refreshFiles(String username, List<String> files) throws RemoteException {

        User user = users.getUser(username);
        int index = users.getUsers().indexOf(user);

        user.setFiles(files);

        ArrayList<User> newUsers = users.getUsers();

        newUsers.set(index, user);
        users.setUsers(newUsers);
    }

    @Override
    public void requestFile(String fileName, String receiverUsername, String hostUsername) throws RemoteException {
        System.out.println("O server recebeu o pedido de transferência");

        User receiverUser = users.getUser(receiverUsername);

        receiverUser.getClientInterface().requestFile(fileName, receiverUsername, hostUsername);
    }

    @Override
    public void sendFile(byte[] content, String fileName, String receiverUsername, String hostUsername) throws RemoteException {
        System.out.println("O server recebeu o ficheiro");

        User hostUser = users.getUser(hostUsername);
        hostUser.getClientInterface().saveFile(content, fileName, receiverUsername, hostUsername);
    }

    @Override
    public void acknowledge(String fileName, String receiverUsername, String hostUsername) throws RemoteException {
        System.out.println("Server recebeu o acknowledge");
        logs.addLog(String.format("%s transferiu o ficheiro '%s' de %s", receiverUsername, fileName, hostUsername));
    }

}

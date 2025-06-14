package servidor;

import java.net.InetAddress;
import java.rmi.server.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import rmi.ClientInterface;
import rmi.Log;
import rmi.Result;
import rmi.ServerInterface;
import rmi.User;

public class Servidor extends UnicastRemoteObject implements ServerInterface {

    static final Users users = new Users();
    static final Logs logs = new Logs();

    public Servidor() throws RemoteException {
        super();
    }

    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("----------------------------------");
            System.out.println("Configuração da Porta");
            System.out.println("----------------------------------");
            System.out.println("1) Usar porta 1099");
            System.out.println("2) Usar porta costumizada");
            System.out.print("\n> ");

            int option = scanner.nextInt();
            int port = 1099;

            if (option == 2) {
                System.out.print("Insira a porta: ");
                port = scanner.nextInt();
            } else if (option != 1) {
                System.out.println("Opção Inválida");
                System.exit(1);
            }

            Servidor server = new Servidor();
            Registry reg = LocateRegistry.createRegistry(port);

            reg.rebind("projeto-sd", server);

            System.out.println("\n\n----------------------------------");
            System.out.println("Dados de conexão");
            System.out.println("----------------------------------");

            System.out.println("Endereço: " + InetAddress.getLocalHost().getHostAddress());
            System.out.println("Porta: " + port);

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Result login(String username, String sharedPath, List<String> files, ClientInterface clientInterface) throws RemoteException {
        synchronized (users) {
            Result result = users.login(username, sharedPath, files, clientInterface);

            if (result.getSuccess()) {
                synchronized (logs) {
                    logs.addLog(username + " fez Login!");
                }
            }

            return result;
        }
    }

    @Override
    public List<User> getUsers() throws RemoteException {
        synchronized (users) {
            return users.getUsers();

        }
    }

    @Override
    public List<Log> getLogs(String username) throws RemoteException {
        synchronized (users) {
            User user = users.getUser(username);

            Long userTimestamp = user.getLoginTimestamp();

            ArrayList<Log> userlogs = new ArrayList();

            synchronized (logs) {
                for (Log log : logs.getLogs()) {

                    if (log.getTimestamp() >= userTimestamp) {

                        userlogs.add(log);
                    }
                }

                return userlogs;
            }
        }
    }

    @Override
    public Result logout(String username) throws RemoteException {
        synchronized (users) {
            Result result = users.logout(username);

            if (result.getSuccess()) {
                synchronized (logs) {
                    logs.addLog(username + " Terminou a Sessao");
                }
            }
            return result;
        }
    }

    @Override
    public void refreshFiles(String username, List<String> files) throws RemoteException {
        synchronized (users) {
            User user = users.getUser(username);
            int index = users.getUsers().indexOf(user);

            user.setFiles(files);

            ArrayList<User> newUsers = users.getUsers();

            newUsers.set(index, user);
            users.setUsers(newUsers);
        }
    }

    @Override
    public void requestFile(String fileName, String receiverUsername, String hostUsername) throws RemoteException {
        System.out.println("O server recebeu o pedido de transferência");

        synchronized (users) {
            User hostUser = users.getUser(hostUsername);
            hostUser.getClientInterface().requestFile(fileName, receiverUsername, hostUsername);
        }
    }

    @Override
    public void sendFile(byte[] content, String fileName, String receiverUsername, String hostUsername) throws RemoteException {
        System.out.println("O server recebeu o ficheiro");

        User receiverUser = users.getUser(receiverUsername);
        receiverUser.getClientInterface().saveFile(content, fileName, receiverUsername, hostUsername);
    }

    @Override
    public void acknowledge(String fileName, String receiverUsername, String hostUsername) throws RemoteException {
        System.out.println("Server recebeu o acknowledge");
        synchronized (logs) {
            logs.addLog(String.format("%s transferiu o ficheiro '%s' de %s", receiverUsername, fileName, hostUsername));
        }
    }

}

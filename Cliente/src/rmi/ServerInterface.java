package rmi;

import java.rmi.*;
import java.util.List;

public interface ServerInterface extends Remote {

    Result login(String username, String sharedPath, List<String> files, ClientInterface clientInterface) throws RemoteException;

    Result logout(String username) throws RemoteException;

    List<User> getUsers() throws RemoteException;

    List<Log> getLogs(String username) throws RemoteException;

    void refreshFiles(String username, List<String> files) throws RemoteException;

    void requestFile(String fileName, String receiverUsername, String hostUsername) throws RemoteException;

    void sendFile(byte[] content, String fileName, String receiverUsername, String hostUsername) throws RemoteException;

    void acknowledge(String fileName, String receiverUsername, String hostUsername) throws RemoteException;
}

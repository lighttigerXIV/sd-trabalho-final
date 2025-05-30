package rmi;

import java.rmi.*;
import java.util.List;

public interface ServerInterface extends Remote {

    Result login(String username, String sharedPath, List<String> files) throws RemoteException;

    Result logout(String username) throws RemoteException;

    List<User> getUsers() throws RemoteException;

    List<Log> getLogs(String username) throws RemoteException;
}

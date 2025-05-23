package rmi;

import java.rmi.*;
import java.util.List;

public interface ServerInterface extends Remote {

    Result login(String username, String sharedPath) throws RemoteException;

    List<User> getUsers() throws RemoteException;
}

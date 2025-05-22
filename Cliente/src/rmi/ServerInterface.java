package rmi;

import java.rmi.*;
import java.util.Map;

public interface ServerInterface extends Remote {

    Map<Boolean, String> login(String username, String sharedPath) throws RemoteException;
}

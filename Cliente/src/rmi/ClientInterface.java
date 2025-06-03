package rmi;

import java.rmi.*;

public interface ClientInterface extends Remote {

    void requestFile(String fileName, String receiverUsername, String hostUsername) throws RemoteException;

    void sendFile(byte[] content, String fileName, String receiverUsername, String hostUsername) throws RemoteException;
}

package cliente;

import java.rmi.RemoteException;
import java.rmi.server.*;
import rmi.ClientInterface;
import rmi.ServerInterface;

public class FileTransfer extends UnicastRemoteObject implements ClientInterface {

    private ServerInterface serverInterface;

    public FileTransfer(ServerInterface serverInterface) throws RemoteException {
        this.serverInterface = serverInterface;
    }

    @Override
    public void requestFile(String fileName, String receiverUsername, String hostUsername) throws RemoteException {
        System.out.println("O cliente recebeu um pedido de transferência");

        serverInterface.sendFile(new byte[2], fileName, receiverUsername, hostUsername);
    }

    /// Recebe os bytes e guarda na pasta
    @Override
    public void sendFile(byte[] content, String fileName, String receiverUsername, String hostUsername) throws RemoteException {
        System.out.println("O cliente recebeu o ficheiro");
        serverInterface.acknowledge(fileName, receiverUsername, hostUsername);
    }

}

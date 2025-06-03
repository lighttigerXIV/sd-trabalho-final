package cliente;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.*;
import rmi.ClientInterface;
import rmi.ServerInterface;

public class FileTransfer extends UnicastRemoteObject implements ClientInterface {

    private ServerInterface serverInterface;
    private File sharedFolder;

    public FileTransfer(ServerInterface serverInterface, File sharedFolder) throws RemoteException {
        this.serverInterface = serverInterface;
        this.sharedFolder = sharedFolder;
    }

    @Override
    public void requestFile(String fileName, String receiverUsername, String hostUsername) throws RemoteException {
        try {

            System.out.println("O cliente recebeu um pedido de transferência");
            System.out.println(sharedFolder);

            // Obter bytes do ficheiro
            byte[] fileBytes = Files.readAllBytes(Paths.get(sharedFolder + "\\" + fileName));

            serverInterface.sendFile(fileBytes, fileName, receiverUsername, hostUsername);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /// Recebe os bytes e guarda na pasta
    @Override
    public void saveFile(byte[] content, String fileName, String receiverUsername, String hostUsername) throws RemoteException {

        try {

            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(content);

            System.out.println("O cliente recebeu o ficheiro");
            serverInterface.acknowledge(fileName, receiverUsername, hostUsername);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

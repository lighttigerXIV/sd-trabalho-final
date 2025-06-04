package cliente;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import rmi.ClientInterface;
import rmi.ServerInterface;

public class FileTransfer extends UnicastRemoteObject implements ClientInterface {

    private ServerInterface serverInterface;
    private File sharedFolder;
    private JFrame mainFrame;

    public FileTransfer(ServerInterface serverInterface, File sharedFolder, JFrame mainFrame) throws RemoteException {
        this.serverInterface = serverInterface;
        this.sharedFolder = sharedFolder;
        this.mainFrame = mainFrame;
    }

    @Override
    public void requestFile(String fileName, String receiverUsername, String hostUsername) throws RemoteException {
        try {

            System.out.println("O cliente recebeu um pedido de transferência");

            // Obter bytes do ficheiro
            byte[] fileBytes = Files.readAllBytes(Paths.get(sharedFolder + "/" + fileName));

            serverInterface.sendFile(fileBytes, fileName, receiverUsername, hostUsername);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /// Recebe os bytes e guarda na pasta
    @Override
    public void saveFile(byte[] content, String fileName, String receiverUsername, String hostUsername) throws RemoteException {

        try {

            String path = sharedFolder + "/" + fileName;
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(content);

            System.out.println("O cliente recebeu o ficheiro");
            serverInterface.acknowledge(fileName, receiverUsername, hostUsername);

            JOptionPane.showMessageDialog(mainFrame, "Ficheiro transferido como sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

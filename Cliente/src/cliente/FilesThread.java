package cliente;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import rmi.ServerInterface;

public class FilesThread implements Runnable {

    private JList filesList;
    private ServerInterface serverInterface;
    private Boolean run;
    private String username;
    private File sharedFolder;

    public FilesThread(JList filesList, ServerInterface serverInterface, String username, File sharedFolder) {
        this.filesList = filesList;
        this.serverInterface = serverInterface;
        this.run = true;
        this.username = username;
        this.sharedFolder = sharedFolder;
    }

    public void kill() {
        filesList.setModel(new DefaultListModel<>());
        run = false;
    }

    public void refreshFiles() {
        try {

            List<String> files = new ArrayList<>();

            Files.newDirectoryStream(sharedFolder.toPath()).forEach(path -> {
                if (new File(path.toString()).isFile()) {
                    files.add(path.toString());
                }
            });

            serverInterface.refreshFiles(username, files);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        do {
            try {
                refreshFiles();
                TimeUnit.SECONDS.sleep(30);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (run);
    }
}

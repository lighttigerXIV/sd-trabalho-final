package cliente;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import rmi.ServerInterface;
import rmi.User;

public class UsersThread implements Runnable {

    private List<User> users;
    private ServerInterface serverInterface;
    private JList clientsList;
    private JList filesList;
    private Boolean run;

    public UsersThread(ServerInterface serverInterface, JList clientsList, JList filesList) {
        this.serverInterface = serverInterface;
        this.clientsList = clientsList;
        this.filesList = filesList;
        this.run = true;
    }

    public void kill() {
        clientsList.setModel(new DefaultListModel<>());
        run = false;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public void run() {
        do {
            try {
                int selectedUsernameIndex = clientsList.getSelectedIndex();
                int selectedFileIndex = filesList.getSelectedIndex();

                String selectedUsername = (selectedUsernameIndex == -1) ? null : clientsList.getSelectedValue().toString();
                String selectedFileName = (selectedFileIndex == -1) ? null : filesList.getSelectedValue().toString();

                users = serverInterface.getUsers();

                DefaultListModel<String> listModel = new DefaultListModel<>();

                users.forEach(user -> {

                    listModel.addElement(user.getUserName());

                });

                clientsList.setModel(listModel);

                DefaultListModel<String> filesModel = new DefaultListModel<>();
                int newSelectedFileIndex = -1;

                if (selectedUsername != null) {
                    User user = users.stream().filter(u -> u.getUserName().equals(selectedUsername)).findFirst().orElse(null);
                    int userIndex = users.indexOf(user);
                    clientsList.setSelectedIndex(userIndex);

                    int tmp = 0;

                    for (String file : user.getFiles()) {
                        String fileName = new File(file).getName();

                        filesModel.addElement(fileName);

                        if (fileName.equals(selectedFileName)) {
                            newSelectedFileIndex = tmp;
                        }

                        tmp++;
                    }
                }

                filesList.setModel(filesModel);

                if (newSelectedFileIndex >= 0) {
                    filesList.setSelectedIndex(newSelectedFileIndex);
                }

                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (run);
    }

}

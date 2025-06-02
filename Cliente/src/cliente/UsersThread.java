package cliente;

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
    private Boolean run;

    public UsersThread(ServerInterface serverInterface, JList clientsList) {
        this.serverInterface = serverInterface;
        this.clientsList = clientsList;
        this.run = true;
    }

    public void kill() {
        clientsList.setModel(new DefaultListModel<>());
        run = false;
    }

    @Override
    public void run() {
        do {
            try {
                users = serverInterface.getUsers();

                DefaultListModel<String> listModel = new DefaultListModel<>();

                users.forEach(user -> {

                    listModel.addElement(user.getUserName());

                });

                clientsList.setModel(listModel);

                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (run);
    }

}

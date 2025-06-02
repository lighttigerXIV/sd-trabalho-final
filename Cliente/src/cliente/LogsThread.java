package cliente;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;
import rmi.Log;
import rmi.ServerInterface;

public class LogsThread implements Runnable {

    private ServerInterface serverInterface;
    private String username;
    private JList logsList;
    private boolean run = true;

    public LogsThread(ServerInterface serverInterface, String username, JList logsList) {
        this.serverInterface = serverInterface;
        this.username = username;
        this.logsList = logsList;
    }

    public void kill() {
        logsList.setModel(new DefaultListModel<>());
        run = false;
    }

    @Override
    public void run() {
        do {
            try {
                List<Log> logs = serverInterface.getLogs(username);
                DefaultListModel<String> model = new DefaultListModel<>();

                logs.forEach(log -> {
                    model.addElement(log.toString());
                });

                logsList.setModel(model);

                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                System.out.println(e);
            }
        } while (run);
    }

}

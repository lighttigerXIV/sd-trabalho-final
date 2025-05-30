package cliente;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import rmi.Log;
import rmi.ServerInterface;

public class LogsThread implements Runnable {

    private ServerInterface serverInterface;
    private String username;
    private JList logsList;

    public LogsThread(ServerInterface serverInterface, String username, JList logsList) {
        this.serverInterface = serverInterface;
        this.username = username;
        this.logsList = logsList;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);

                List<Log> logs = serverInterface.getLogs(username);
                DefaultListModel<String> model = new DefaultListModel<>();

                logs.forEach(log -> {
                    model.addElement(log.toString());
                });

                logsList.setModel(model);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}

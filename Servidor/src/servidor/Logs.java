package servidor;

import java.util.ArrayList;
import rmi.Log;

public class Logs {

    ArrayList<Log> logs;

    public Logs() {
        this.logs = new ArrayList<>();
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public void addLog(String message) {
        Log log = new Log(message);

        logs.add(log);

    }
}

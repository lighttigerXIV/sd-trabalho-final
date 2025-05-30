package rmi;

public class Log {

    Long timestamp;
    String message;

    public Log(String message) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

}

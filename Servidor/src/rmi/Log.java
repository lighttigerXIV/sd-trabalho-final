package rmi;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Log implements Serializable {

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

    @Override
    public String toString() {
        Instant instant = Instant.ofEpochSecond(timestamp);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);

        return String.format("%s %s", formattedDateTime, message);
    }

}

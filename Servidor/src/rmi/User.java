package rmi;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    String userName;
    String sharedPath;
    Long loginTimestamp;
    List<String> files;

    public User(String userName, String sharedPath, List<String> files) {
        this.userName = userName;
        this.sharedPath = sharedPath;
        this.files = files;
        this.loginTimestamp = System.currentTimeMillis() / 1000;
    }

    public String getUserName() {
        return userName;
    }

    public String getSharedPath() {
        return sharedPath;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSharedPath(String sharedPath) {
        this.sharedPath = sharedPath;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public Long getLoginTimestamp() {
        return loginTimestamp;
    }

}

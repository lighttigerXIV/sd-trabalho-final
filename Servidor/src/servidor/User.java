package servidor;

public class User {

    String userName;
    String sharedPath;

    public User(String userName, String sharedPath) {
        this.userName = userName;
        this.sharedPath = sharedPath;
    }

    public String getUserName() {
        return userName;
    }

    public String getSharedPath() {
        return sharedPath;
    }

    public void setSharedPath(String sharedPath) {
        this.sharedPath = sharedPath;
    }

}

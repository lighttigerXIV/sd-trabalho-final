
public class User {

    private String username;
    private String sharedFolder;

    public User(String username, String sharedFolder) {
        this.username = username;
        this.sharedFolder = sharedFolder;
    }

    public String getUsername() {
        return username;
    }

    public String getSharedFolder() {
        return sharedFolder;
    }

}

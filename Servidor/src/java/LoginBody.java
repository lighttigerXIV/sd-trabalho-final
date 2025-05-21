
public class LoginBody {

    private String username;
    private String sharedFolder;

    public LoginBody() {
    }

    public LoginBody(String username, String sharedFolder) {
        this.username = username;
        this.sharedFolder = sharedFolder;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setSharedFolder(String sharedFolder) {
        this.sharedFolder = sharedFolder;
    }

    public String getSharedFolder() {
        return sharedFolder;
    }

}

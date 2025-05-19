package cliente;


public class LoginBody {

    private String username;

    public LoginBody() {
    }

    public LoginBody(String username) {
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}

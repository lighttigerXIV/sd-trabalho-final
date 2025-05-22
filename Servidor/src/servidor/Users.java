package servidor;

import java.util.ArrayList;

public class Users {

    ArrayList<User> users;

    public Users() {
        this.users = new ArrayList<>();
    }

    private User getUser(String username) {
        return users.stream()
                .filter(user -> user.getUserName().toLowerCase().equals(username.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    public boolean login() {

        return true;
    }
}

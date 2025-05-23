package servidor;

import rmi.User;
import java.util.ArrayList;
import rmi.Result;

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

    public ArrayList<User> getUsers() {
        return users;
    }

    public Result login(String username, String sharedPath) {
        User user = getUser(username);

        if (user != null) {
            return new Result(false, "Utilizador já existe");
        }

        users.add(new User(username, sharedPath));

        return new Result(true, "Utilizador adicionado com sucesso");
    }
}

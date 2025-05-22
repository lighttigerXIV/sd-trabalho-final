package servidor;

import java.util.ArrayList;
import java.util.Map;

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

    public Map<Boolean, String> login(String username, String sharedPath) {
        User user = getUser(username);

        if (user != null) {
            return Map.of(false, "Utilizador ja existe");
        }

        users.add(new User(username, sharedPath));

        return Map.of(true, "Utilizador adicionado com sucesso");
    }
}

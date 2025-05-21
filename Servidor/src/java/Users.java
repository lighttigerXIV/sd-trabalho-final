
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/// Referencias
/// Stream -> https://www.geeksforgeeks.org/stream-in-java/
/// Stream Filter -> https://www.baeldung.com/find-list-element-java#5-java-8-stream-api
/// Stream Map -> https://www.geeksforgeeks.org/stream-map-java-examples/

@Path("/users")
public class Users {

    private final ArrayList<User> users = new ArrayList();

    /// Retorna o utilizador se for encontrado.
    /// Caso não seja encontrado retorna um null
    private User getUser(String username) {
        return users.stream()
                .filter(user -> user.getUsername().toLowerCase().equals(username.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public Response getUsers() {
        List<String> usernames = users.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        return Response.status(200).entity(usernames).build();
    }

    @POST
    @Path("login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginBody body) {

        if (body == null) {
            return new MessageResponse(400, "É necessário enviar um body").response();
        }

        String username = body.getUsername();

        if (username == null || username.trim().equals("")) {
            return new MessageResponse(400, "É necessário enviar um 'username'").response();
        }

        User user = getUser(username);

        if (user != null) {
            return new MessageResponse(409, "Já existe um utilizador com o mesmo username").response();
        }

        User newUser = new User(username);

        users.add(newUser);

        return new MessageResponse("Utilizador adicionado com sucesso").response();
    }

    @DELETE
    @Path("logout/{username}")
    @Produces("application/json")
    public Response logout(@PathParam("username") String username) {
        if (username.trim().isEmpty()) {
            return new MessageResponse(400, "Username inválido").response();
        }

        User user = getUser(username);

        if (user == null) {
            return new MessageResponse(404, "Username não encontrado").response();
        }

        users.remove(user);

        return new MessageResponse("Logout feito com sucesso").response();
    }
}


import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class App extends Application {

    private Set<Object> singletons = new HashSet<Object>();

    public App() {
        singletons.add(new Users());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}

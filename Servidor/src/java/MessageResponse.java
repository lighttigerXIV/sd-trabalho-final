
import jakarta.ws.rs.core.Response;

public class MessageResponse {

    private int code;
    private String message;

    public MessageResponse() {
    }

    public MessageResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public MessageResponse(String message) {
        this.code = 200;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Response response() {
        return Response.status(code).entity(this).build();
    }
}

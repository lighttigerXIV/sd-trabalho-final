package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    Users users = new Users();
    Logs logs = new Logs();

    public static void main(String[] args) {

        try {

            ServerSocket serversocket = new ServerSocket(13);

            while (true) {
                Socket socket = serversocket.accept();
                System.out.println("Remoto: " + socket.getInetAddress().getHostAddress() + " Port: " + socket.getPort());
                System.out.println("Local : " + socket.getLocalAddress().getHostAddress() + " Port: " + socket.getLocalPort());
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            }

        } catch (Exception e) {
        }

    }

}

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program started");
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            serverSocket.setReuseAddress(true);

            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection: listening on port 8080");
        } catch (IOException e) {
            System.out.println("IOException:" +  e.getMessage());
        }
    }
}

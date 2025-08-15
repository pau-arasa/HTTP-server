import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import HttpUtils.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program started");
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            serverSocket.setReuseAddress(true);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                OutputStream out = clientSocket.getOutputStream();
                InputStream in = clientSocket.getInputStream();
                System.out.println("Accepted connection: listening on port 8080");

                String requestLine = HttpReader.ReadLine(in);
                System.out.println("Received request: " + requestLine);

                String[] parts = requestLine.split(" ", 3);
                if (requestLine.isEmpty() || parts.length != 3) {
                    //Handle request with bad request line: read and ignore the rest of lines,
                    //then continue to end the connection
                    while(!HttpReader.ReadLine(in).isEmpty() );
                    String response = "HTTP/1.1 404 Not Found\r\n\r\n";
                    out.write(response.getBytes(StandardCharsets.US_ASCII));
                    out.flush();

                    clientSocket.close();
                    continue;
                }

                HttpRequestLine request =  new HttpRequestLine(parts[0], parts[1], parts[2]);

                String line;
                while (!(line = HttpReader.ReadLine(in) ).isEmpty() ) {
                    System.out.println(line);
                    //Ignore other lines for now
                }

                String response = "HTTP/1.1 200 OK\r\n\r\n";
                out.write(response.getBytes(StandardCharsets.US_ASCII));
                out.flush();

                clientSocket.close();
            }

        } catch (IOException e) {
            System.out.println("IOException:" +  e.getMessage());
        }
    }
}

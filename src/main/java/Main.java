import dto.RequestDto;
import service.MapLoader;
import utils.HttpParserUtils;
import utils.HttpProcessUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        // System.out.println("Logs from your program will appear here!");
        MapLoader.load();

        try {
            ServerSocket serverSocket = new ServerSocket(4221);

            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            Socket socket = serverSocket.accept(); // Wait for connection from client.

             // parse the input stream
             InputStream inputStream = socket.getInputStream();
             RequestDto requestDto = HttpParserUtils.parseHttpRequest(inputStream);

             // process the request
             String response = HttpProcessUtils.process(requestDto);

             // send response to output stream
             OutputStream outputStream = socket.getOutputStream();
             outputStream.write(response.getBytes(StandardCharsets.UTF_8));
             outputStream.flush();

             System.out.println("accepted new connection");
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}

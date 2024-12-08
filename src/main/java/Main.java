import utils.HttpUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

     try {
         ServerSocket serverSocket = new ServerSocket(4221);

         // Since the tester restarts your program quite often, setting SO_REUSEADDR
         // ensures that we don't run into 'Address already in use' errors
         serverSocket.setReuseAddress(true);

         Socket socket = serverSocket.accept(); // Wait for connection from client.
         OutputStream outputStream = socket.getOutputStream();
         outputStream.write(HttpUtils.getStatus().getBytes(StandardCharsets.UTF_8));

         System.out.println("accepted new connection");
     } catch (IOException e) {
         System.out.println("IOException: " + e.getMessage());
     }
  }
}

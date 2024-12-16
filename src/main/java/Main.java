import service.HttpPath;
import service.SocketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        // System.out.println("Logs from your program will appear here!");
        HttpPath.registerPath();
        SocketServer.addDirectoryIfExist(args);

        try {
            ServerSocket serverSocket = new ServerSocket(4221);

            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept(); // Wait for connection from client.
                SocketServer.handleConnectionAsync(socket);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}

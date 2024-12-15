package service;

import constants.OutputConstants;
import dto.RequestDto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class SocketServer {

    public static void handleConnectionAsync(Socket socket) {
        new Thread(() -> {
            try {
                handleConnection(socket);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    socket.close();
                } catch (IOException ignore) {

                }
            }
        }).start();
    }

    private static void handleConnection(Socket socket) throws IOException, InterruptedException {
        while (!socket.isClosed()) {
            // parse the input stream
            InputStream inputStream = socket.getInputStream();
            RequestDto requestDto = HttpRequest.parseRequest(inputStream);

            // process the request
            String response = HttpResponse.process(requestDto);

            // send response to output stream
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            Thread.sleep(Duration.of(OutputConstants.THREAD_SLEEP_100_MICROS, ChronoUnit.MICROS));
        }
    }
}

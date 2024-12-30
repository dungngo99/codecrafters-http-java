package service;

import constants.OutputConstants;
import dto.RequestDto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            HttpRequest.handleHeaders(requestDto);

            // process the request
            String response = HttpResponse.process(requestDto);

            // send response to output stream
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            Thread.sleep(Duration.of(OutputConstants.THREAD_SLEEP_100_MICROS, ChronoUnit.MICROS));
        }
    }

    public static void addDirectoryIfExist(String[] args) {
        if (args.length < 2) {
            return;
        }
        String directoryKey = args[0].substring(OutputConstants.APP_ARGS_DIRECTORY_KEY_START_INDEX);
        String value = args[1];

        Path path = Paths.get(value);
        String absPath = path.toAbsolutePath().toString();
        File file = path.toFile();
        if (!file.exists()) {
            file.mkdirs();
        }

        setArgs2SystemProperty(directoryKey, absPath);
    }

    public static void setArgs2SystemProperty(String key, String value) {
        System.setProperty(key, value);
    }

    public static String getSystemProperty(String key) {
        return System.getProperty(key);
    }
}

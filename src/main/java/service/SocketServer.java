package service;

import constants.Constants;
import dto.RequestDto;
import dto.ResponseDto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

public class SocketServer {

    public static void handleConnectionAsync(Socket socket) {
        new Thread(() -> {
            try (socket) {
                handleConnection(socket);
            } catch (IOException | InterruptedException | RuntimeException e) {
                System.out.println("failed to handle socket connection due to " + e.getMessage());
            }
        }).start();
    }

    private static void handleConnection(Socket socket) throws IOException, InterruptedException {
        while (!socket.isClosed()) {
            // parse the input stream
            InputStream inputStream = socket.getInputStream();
            RequestDto requestDto = HttpRequest.parseRequest(inputStream);
            HttpRequest.fillHeaders(requestDto);

            // process the request
            ResponseDto responseDto = HttpResponse.process(requestDto);
            HttpResponse.fillCommonResponseHeaders(requestDto, responseDto);

            // send response to output stream
            byte[] responseBytes = ByteHelper.convertToByteStream(responseDto);
            ByteHelper.writeThenFlushStream(socket, responseBytes);

            // handle post connection
            handlePostConnection(socket, responseDto);
            Thread.sleep(Duration.of(Constants.THREAD_SLEEP_100_MICROS, ChronoUnit.MICROS));
        }
    }

    private static void handlePostConnection(Socket socket, ResponseDto responseDto) throws IOException {
        Map<String, String> headers = responseDto.getHeaders();
        if (Objects.isNull(headers)
                || !headers.containsKey(Constants.CONNECTION)
                || !Objects.equals(headers.get(Constants.CONNECTION), Constants.CONNECTION_CLOSE)) {
            return;
        }
        socket.close();
    }

    public static void addDirectoryIfExist(String[] args) {
        if (args.length < 2) {
            return;
        }
        String directoryKey = args[0].substring(Constants.APP_ARGS_DIRECTORY_KEY_START_INDEX);
        String value = args[1];

        Path path = Paths.get(value);
        String absPath = path.toAbsolutePath().toString();
        File file = path.toFile();
        if (!file.exists()) {
            boolean ignored = file.mkdirs();
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

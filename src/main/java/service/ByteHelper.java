package service;

import constants.Constants;
import dto.ResponseDto;
import enums.CompressScheme;
import utils.HttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ByteHelper {

    public static String readNextString(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        int next;
        while ((next = reader.read()) != -1) {
            char c = (char) next;
            if (c == Constants.CR) {
                long ignored = reader.skip(Constants.CRLF_LENGTH_SKIP);
                break;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    public static String readNextStringLengthN(BufferedReader reader, int N) throws IOException {
        StringBuilder builder = new StringBuilder();
        int next;
        while (N>0 && (next = reader.read()) != -1) {
            char c = (char) next;
            builder.append((c));
            N--;
        }
        return builder.toString();
    }


    public static byte[] convertToByteStream(ResponseDto responseDto) {
        Map<String, String> headerMap = responseDto.getHeaders();
        String compressionScheme = headerMap.get(Constants.CONTENT_ENCODING);

        if (CompressScheme.GZIP.name().equalsIgnoreCase(compressionScheme)) {
            byte[] var1 = (responseDto.getStatusLine() + Constants.CRLF).getBytes(StandardCharsets.UTF_8);
            byte[] var3 = HttpUtils.gzipCompress(responseDto.getBody());

            headerMap.put(Constants.CONTENT_LENGTH, String.valueOf(var3.length));
            String headers = HttpUtils.convertFromHeaders(headerMap);
            byte[] var2 = (headers + Constants.CRLF).getBytes(StandardCharsets.UTF_8);

            return HttpUtils.concatTripleBytes(var1, var2, var3);
        } else {
            String headers = Constants.EMPTY_STRING;
            if (!headerMap.isEmpty()) {
                headers = HttpUtils.convertFromHeaders(headerMap);
            }
            List<String> list = List.of(responseDto.getStatusLine(), headers, responseDto.getBody());
            System.out.println(HttpUtils.fromList(list));
            return HttpUtils.fromList(list).getBytes(StandardCharsets.UTF_8);
        }
    }

    public static void writeThenFlushStream(Socket socket, byte[] bytes) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
    }
}

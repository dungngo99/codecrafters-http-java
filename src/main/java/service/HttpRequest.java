package service;

import dto.RequestDto;
import enums.CompressScheme;
import enums.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

import static constants.Constants.*;

public class HttpRequest {

    public static RequestDto parseRequest(InputStream inputStream) {
        RequestDto requestDto = new RequestDto();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            fillRequestLine(bufferedReader, requestDto);
            fillHeaders(bufferedReader, requestDto);
            fillBody(bufferedReader, requestDto);
        } catch (IOException e) {
            System.out.printf("failed to parse http request due to, error=%s\n", e.getMessage());
        }
        return requestDto;
    }

    private static void fillRequestLine(BufferedReader reader, RequestDto requestDto) throws IOException {
        String requestLine = ByteHelper.readNextString(reader);
        String[] requestLines = requestLine.split(TAB);
        requestDto.setHttpMethod(HttpMethod.ofString(requestLines[HTTP_METHOD_INDEX]));
        requestDto.setTargetMethod(requestLines[HTTP_TARGET_METHOD_INDEX]);
        requestDto.setHttpVersion(requestLines[HTTP_VERSION_INDEX]);
    }

    private static void fillHeaders(BufferedReader reader, RequestDto requestDto) throws IOException {
        while (true) {
            String header = ByteHelper.readNextString(reader);
            if (header.isBlank()) {
                break;
            }
            String[] pair = header.split(REQUEST_HEADER_DELIMITER);
            String key = pair[REQUEST_HEADER_KEY_INDEX].strip();
            String value = pair[REQUEST_HEADER_VALUE_INDEX].strip();
            requestDto.getHeaders().put(key, value);
        }
    }

    private static void fillBody(BufferedReader reader, RequestDto requestDto) throws IOException {
        String contentLength = requestDto.getHeaders().get(CONTENT_LENGTH);
        if (contentLength == null || contentLength.isEmpty()) {
            return;
        }
        int l = Integer.parseInt(contentLength);
        String body = ByteHelper.readNextStringLengthN(reader, l);
        requestDto.setBody(body);
    }

    public static void handleHeaders(RequestDto requestDto) {
        Map<String, String> headers = requestDto.getHeaders();
        if (Objects.isNull(headers) || !headers.containsKey(ACCEPT_ENCODING)) {
            return;
        }
        String acceptEncodings = headers.get(ACCEPT_ENCODING);
        if (acceptEncodings.isBlank()) {
            headers.remove(ACCEPT_ENCODING);
            return;
        }
        String[] acceptEncodingArr = acceptEncodings.split(COMMA_SPACE_DELIMITER);
        StringJoiner joiner = new StringJoiner(COMMA_SPACE_DELIMITER);
        for (String acceptEncoding_: acceptEncodingArr) {
            if (CompressScheme.isValid(acceptEncoding_)) {
                joiner.add((acceptEncoding_));
            }
        }
        if (joiner.toString().isBlank()) {
            headers.remove(ACCEPT_ENCODING);
            return;
        }
        headers.put(ACCEPT_ENCODING, joiner.toString());
    }
}

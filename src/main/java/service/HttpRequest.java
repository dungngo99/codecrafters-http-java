package service;

import dto.RequestDto;
import enums.CompressScheme;
import enums.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringJoiner;

import static constants.OutputConstants.*;

public class HttpRequest {

    public static RequestDto parseRequest(InputStream inputStream) {
        RequestDto requestDto = new RequestDto();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedReaderHelper helper = new BufferedReaderHelper(bufferedReader);
            if (!helper.canRead()) {
                return requestDto;
            }

            fillRequestLine(helper, requestDto);
            fillHeaders(helper, requestDto);
            fillBody(helper, requestDto);
        } catch (IOException e) {
            System.out.printf("failed to parse http request due to, error=%s\n", e.getMessage());
        }
        return requestDto;
    }

    private static void fillRequestLine(BufferedReaderHelper helper, RequestDto requestDto) {
        char[] chars = helper.readNextChars();
        String requestLine = new String(chars);
        String[] requestLines = requestLine.split(TAB);
        requestDto.setHttpMethod(HttpMethod.ofString(requestLines[HTTP_METHOD_INDEX]));
        requestDto.setTargetMethod(requestLines[HTTP_TARGET_METHOD_INDEX]);
        requestDto.setHttpVersion(requestLines[HTTP_VERSION_INDEX]);
    }

    private static void fillHeaders(BufferedReaderHelper helper, RequestDto requestDto) {
        while (true) {
            char[] chars = helper.readNextChars();
            if (chars.length == 0) {
                break;
            }
            String header = new String(chars);
            String[] pair = header.split(REQUEST_HEADER_DELIMITER);
            String key = pair[REQUEST_HEADER_KEY_INDEX].strip();
            String value = pair[REQUEST_HEADER_VALUE_INDEX].strip();
            requestDto.getHeaders().put(key, value);
        }
    }

    private static void fillBody(BufferedReaderHelper helper, RequestDto requestDto) {
        String contentLength = requestDto.getHeaders().get(CONTENT_LENGTH);
        if (contentLength == null || contentLength.isEmpty()) {
            return;
        }
        Integer l = Integer.parseInt(contentLength);
        char[] chars = helper.readNChars(l);
        String body = new String(chars);
        requestDto.setBody(body);
    }

    public static void handleHeaders(RequestDto requestDto) {
        Map<String, String> headers = requestDto.getHeaders();
        if (headers == null || !headers.containsKey(ACCEPT_ENCODING)) {
            return;
        }
        String acceptEncodings = headers.get(ACCEPT_ENCODING);
        if (acceptEncodings.isBlank()) {
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

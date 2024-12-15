package service;

import constants.OutputConstants;
import dto.RequestDto;
import enums.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static constants.OutputConstants.*;

public class HttpRequest {

    public static RequestDto parseRequest(InputStream inputStream) {
        RequestDto requestDto = new RequestDto();
        try {
            List<String> list = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (bufferedReader.ready()) {
                list.add(bufferedReader.readLine());
            }
            if (list.isEmpty()) {
                return requestDto;
            }
            fillRequestLine(list.get(HTTP_METHOD_STATUS_LINE_INDEX), requestDto);
            fillHeaders(list.subList(HTTP_METHOD_HEADER_INDEX_START, list.size()+HTTP_METHOD_HEADER_INDEX_END), requestDto);
        } catch (IOException e) {
            System.out.printf("failed to parse http request due to, error=%s\n", e.getMessage());
        }
        return requestDto;
    }

    private static void fillRequestLine(String requestLine, RequestDto requestDto) {
        String[] requestLines = requestLine.split(OutputConstants.TAB);
        requestDto.setHttpMethod(HttpMethod.ofString(requestLines[HTTP_METHOD_INDEX]));
        requestDto.setTargetMethod(requestLines[HTTP_TARGET_METHOD_INDEX]);
        requestDto.setHttpVersion(requestLines[HTTP_VERSION_INDEX]);
    }

    private static void fillHeaders(List<String> headers, RequestDto requestDto) {
        for (int i=0; i<headers.size(); i++) {
            String[] pair = headers.get(i).split(REQUEST_HEADER_DELIMITER);
            String key = pair[REQUEST_HEADER_KEY_INDEX].strip();
            String value = pair[REQUEST_HEADER_VALUE_INDEX].strip();
            requestDto.getHeaders().put(key, value);
        }
    }
}

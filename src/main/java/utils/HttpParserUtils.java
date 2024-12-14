package utils;

import constants.OutputConstants;
import dto.RequestDto;
import enums.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpParserUtils {

    public static RequestDto parseHttpRequest(InputStream inputStream) {
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
            fillRequestLine(list.get(0), requestDto);
        } catch (IOException e) {
            System.out.printf("failed to parse http request due to, error=%s\n", e.getMessage());
        }
        return requestDto;
    }

    private static void fillRequestLine(String requestLine, RequestDto requestDto) {
        String[] requestLines = requestLine.split(OutputConstants.TAB);
        requestDto.setHttpMethod(HttpMethod.ofString(requestLines[0]));
        requestDto.setTargetMethod(requestLines[1]);
        requestDto.setHttpVersion(requestLines[2]);
    }
}

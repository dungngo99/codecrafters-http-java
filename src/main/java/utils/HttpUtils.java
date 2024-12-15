package utils;

import constants.OutputConstants;
import enums.StatusCode;

import java.util.List;
import java.util.StringJoiner;

public class HttpUtils {

    public static String fromList(List<String> list) {
        StringJoiner joiner = new StringJoiner(OutputConstants.CRLF, OutputConstants.EMPTY_STRING, OutputConstants.CRLF);
        for (String str: list) {
            joiner.add(str);
        }
        return joiner.toString();
    }

    public static String getOKStatus() {
        StringJoiner status = new StringJoiner(OutputConstants.TAB);
        status
                .add(OutputConstants.HTTP_VERSION)
                .add(String.valueOf(StatusCode.OK.getCode()))
                .add(StatusCode.OK.getMessage());
        List<String> list = List.of(status.toString(), OutputConstants.EMPTY_STRING);
        return fromList(list);
    }

    public static String getNotFoundStatus() {
        StringJoiner status = new StringJoiner(OutputConstants.TAB);
        status
                .add(OutputConstants.HTTP_VERSION)
                .add(String.valueOf(StatusCode.NOT_FOUND.getCode()))
                .add(StatusCode.NOT_FOUND.getMessage());
        List<String> list = List.of(status.toString(), OutputConstants.EMPTY_STRING);
        return fromList(list);
    }

    public static String getHeader(String key, String value) {
        StringJoiner header = new StringJoiner(OutputConstants.TAB);
        header
                .add(key + OutputConstants.COLON)
                .add(value);
        return header.toString();
    }

    public static String getResponseWithBodyAsPlainText(String text) {
        StringJoiner statusLine = new StringJoiner(OutputConstants.TAB);
        statusLine
                .add(OutputConstants.HTTP_VERSION)
                .add(String.valueOf(StatusCode.OK.getCode()))
                .add(StatusCode.OK.getMessage());
        StringJoiner headers = new StringJoiner(OutputConstants.CRLF, OutputConstants.EMPTY_STRING, OutputConstants.CRLF);
        headers
                .add(getHeader(OutputConstants.CONTENT_TYPE, OutputConstants.contentTypeTextPlain))
                .add(getHeader(OutputConstants.CONTENT_LENGTH, String.valueOf(text.length())));
        String body = text;
        List<String> list = List.of(statusLine.toString(), headers.toString(), body);
        return fromList(list);
    }
}

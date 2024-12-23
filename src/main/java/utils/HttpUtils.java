package utils;

import constants.OutputConstants;
import enums.ContentType;
import enums.StatusCode;

import java.nio.charset.StandardCharsets;
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
        StringJoiner headers = new StringJoiner(OutputConstants.CRLF, OutputConstants.EMPTY_STRING, OutputConstants.CRLF);
        headers
                .add(getHeader(OutputConstants.CONTENT_LENGTH, String.valueOf(OutputConstants.EMPTY_CONTENT_LENGTH)));
        List<String> list = List.of(status.toString(), headers.toString(), OutputConstants.EMPTY_STRING);
        return fromList(list);
    }

    public static String getCreatedStatus() {
        StringJoiner status = new StringJoiner(OutputConstants.TAB);
        status
                .add(OutputConstants.HTTP_VERSION)
                .add(String.valueOf(StatusCode.CREATED.getCode()))
                .add(StatusCode.CREATED.getMessage());
        StringJoiner headers = new StringJoiner(OutputConstants.CRLF, OutputConstants.EMPTY_STRING, OutputConstants.CRLF);
        headers
                .add(getHeader(OutputConstants.CONTENT_LENGTH, String.valueOf(OutputConstants.EMPTY_CONTENT_LENGTH)));
        List<String> list = List.of(status.toString(), headers.toString(), OutputConstants.EMPTY_STRING);
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
                .add(getHeader(OutputConstants.CONTENT_TYPE, ContentType.PLAIN_TEXT.getContentType()))
                .add(getHeader(OutputConstants.CONTENT_LENGTH, String.valueOf(text.length())));
        String body = text;
        List<String> list = List.of(statusLine.toString(), headers.toString(), body);
        return fromList(list);
    }

    public static String getResponseWithBodyAsByteStream(byte[] bytes) {
        StringJoiner statusLine = new StringJoiner(OutputConstants.TAB);
        statusLine
                .add(OutputConstants.HTTP_VERSION)
                .add(String.valueOf(StatusCode.OK.getCode()))
                .add(StatusCode.OK.getMessage());
        StringJoiner headers = new StringJoiner(OutputConstants.CRLF, OutputConstants.EMPTY_STRING, OutputConstants.CRLF);
        headers
                .add(getHeader(OutputConstants.CONTENT_TYPE, ContentType.APPLICATION_OCTET_STREAM.getContentType()))
                .add(getHeader(OutputConstants.CONTENT_LENGTH, String.valueOf(bytes.length)));
        String body = new String(bytes, StandardCharsets.UTF_8);
        List<String> list = List.of(statusLine.toString(), headers.toString(), body);
        return fromList(list);
    }
}

package utils;

import constants.OutputConstants;
import dto.ResponseDto;
import enums.ContentType;
import enums.StatusCode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.GZIPOutputStream;

public class HttpUtils {

    public static String fromList(List<String> list) {
        StringJoiner joiner = new StringJoiner(OutputConstants.CRLF, OutputConstants.EMPTY_STRING, OutputConstants.CRLF);
        for (String str: list) {
            joiner.add(str);
        }
        return joiner.toString();
    }

    public static ResponseDto getOKResponse() {
        StringJoiner status = new StringJoiner(OutputConstants.TAB);
        status
                .add(OutputConstants.HTTP_VERSION)
                .add(String.valueOf(StatusCode.OK.getCode()))
                .add(StatusCode.OK.getMessage());
        String statusLine = status.toString();
        Map<String, String> headers = new HashMap<>();
        String body = OutputConstants.EMPTY_STRING;
        return new ResponseDto(statusLine, headers, body);
    }

    public static ResponseDto getNotFoundResponse() {
        StringJoiner status = new StringJoiner(OutputConstants.TAB);
        status
                .add(OutputConstants.HTTP_VERSION)
                .add(String.valueOf(StatusCode.NOT_FOUND.getCode()))
                .add(StatusCode.NOT_FOUND.getMessage());
        String statusLine = status.toString();
        Map<String, String> headers = new HashMap<>();
        headers.put(OutputConstants.CONTENT_LENGTH, String.valueOf(OutputConstants.EMPTY_CONTENT_LENGTH));
        String body = OutputConstants.EMPTY_STRING;
        return new ResponseDto(statusLine, headers, body);
    }

    public static ResponseDto getCreatedResponse() {
        StringJoiner status = new StringJoiner(OutputConstants.TAB);
        status
                .add(OutputConstants.HTTP_VERSION)
                .add(String.valueOf(StatusCode.CREATED.getCode()))
                .add(StatusCode.CREATED.getMessage());
        String statusLine = status.toString();
        Map<String, String> headers = new HashMap<>();
        headers.put(OutputConstants.CONTENT_LENGTH, String.valueOf(OutputConstants.EMPTY_CONTENT_LENGTH));
        String body = OutputConstants.EMPTY_STRING;
        return new ResponseDto(statusLine, headers, body);
    }

    public static ResponseDto getResponseWithBodyAsPlainText(String text) {
        StringJoiner status = new StringJoiner(OutputConstants.TAB);
        status
                .add(OutputConstants.HTTP_VERSION)
                .add(String.valueOf(StatusCode.OK.getCode()))
                .add(StatusCode.OK.getMessage());
        String statusLine = status.toString();
        Map<String, String> headers = new HashMap<>();
        headers.put(OutputConstants.CONTENT_TYPE, ContentType.PLAIN_TEXT.getContentType());
        headers.put(OutputConstants.CONTENT_LENGTH, String.valueOf(text.length()));
        String body = text;
        return new ResponseDto(statusLine, headers, body);
    }

    public static ResponseDto getResponseWithBodyAsByteStream(byte[] bytes) {
        StringJoiner status = new StringJoiner(OutputConstants.TAB);
        status
                .add(OutputConstants.HTTP_VERSION)
                .add(String.valueOf(StatusCode.OK.getCode()))
                .add(StatusCode.OK.getMessage());
        String statusLine = status.toString();
        Map<String, String> headers = new HashMap<>();
        headers.put(OutputConstants.CONTENT_TYPE, ContentType.APPLICATION_OCTET_STREAM.getContentType());
        headers.put(OutputConstants.CONTENT_LENGTH, String.valueOf(bytes.length));
        String body = new String(bytes, StandardCharsets.UTF_8);
        return new ResponseDto(statusLine, headers, body);
    }

    public static String convertFromHeaders(Map<String, String> headers) {
        StringJoiner joiner1 = new StringJoiner(OutputConstants.CRLF, OutputConstants.EMPTY_STRING, OutputConstants.CRLF);
        for (Map.Entry<String, String> entry: headers.entrySet()) {
            StringJoiner joiner2 = new StringJoiner(OutputConstants.TAB);
            joiner2
                    .add(entry.getKey() + OutputConstants.COLON)
                    .add(entry.getValue());
            joiner1.add(joiner2.toString());
        }
        return joiner1.toString();
    }

    public static byte[] gzipCompress(String str) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
            gzipOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public static byte[] concatTripleBytes(byte[] bytes1, byte[] bytes2, byte[] bytes3) {
        int l1 = bytes1.length;
        int l2 = bytes2.length;
        int l3 = bytes3.length;
        byte[] bytes = Arrays.copyOf(bytes1, l1 + l2 + l3);
        System.arraycopy(bytes2, 0, bytes, l1, l2);
        System.arraycopy(bytes3, 0, bytes, l1 + l2, l3);
        return bytes;
    }
}

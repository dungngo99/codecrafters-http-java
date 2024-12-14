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

    public  static String getNotFoundStatus() {
        StringJoiner status = new StringJoiner(OutputConstants.TAB);
        status
                .add(OutputConstants.HTTP_VERSION)
                .add(String.valueOf(StatusCode.NOT_FOUND.getCode()))
                .add(StatusCode.NOT_FOUND.getMessage());
        List<String> list = List.of(status.toString(), OutputConstants.EMPTY_STRING);
        return fromList(list);
    }
}

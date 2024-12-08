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

    public static String getStatus() {
        StringJoiner status = new StringJoiner(OutputConstants.TAB);
        status
                .add(OutputConstants.HTTP_VERSION)
                .add(StatusCode.OK.getCode())
                .add(StatusCode.OK.name());
        List<String> list = List.of(status.toString(), OutputConstants.EMPTY_STRING);
        return fromList(list);
    }
}

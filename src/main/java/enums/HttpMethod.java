package enums;

import constants.OutputConstants;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    PATCH,
    OPTIONS;

    public static String ofString(String s) {
        for (HttpMethod method: values()) {
            if (method.name().equalsIgnoreCase(s)) {
                return method.name();
            }
        }
        return OutputConstants.EMPTY_STRING;
    }
}

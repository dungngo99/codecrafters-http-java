package enums;

public enum StatusCode {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found");

    private final Integer code;
    private final String message;

    StatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

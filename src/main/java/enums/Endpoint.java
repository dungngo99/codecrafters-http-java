package enums;

public enum Endpoint {

    NOT_FOUND("", true),
    ROOT("/", false),
    ECHO("/echo", true),
    ECHO_STR("/echo/{str}", false),
    USER_AGENT("/user-agent", true),
    FILE("/files", true),
    FILE_STR("/files/{filename}", false);

    private String path;
    private boolean isTerminal;

    Endpoint(String path, boolean isTerminal) {
        this.path = path;
        this.isTerminal = isTerminal;
    }

    public String getPath() {
        return path;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

}

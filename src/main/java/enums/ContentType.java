package enums;

public enum ContentType {

    PLAIN_TEXT("text/plain"),
    APPLICATION_OCTET_STREAM("application/octet-stream");

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}

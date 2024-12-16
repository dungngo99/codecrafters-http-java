package dto;

import enums.ContentType;
import handler.PathHandler;

import java.util.Map;

public class RequestContextDto {
    private String[] targets;
    private PathHandler pathHandler;
    private Map<String, String> headers;
    private String requestMethod;
    private String body;

    public RequestContextDto(String[] targets, PathHandler pathHandler) {
        this.targets = targets;
        this.pathHandler = pathHandler;
    }

    public String[] getTargets() {
        return targets;
    }

    public void setTargets(String[] targets) {
        this.targets = targets;
    }

    public PathHandler getPathHandler() {
        return pathHandler;
    }

    public void setPathHandler(PathHandler pathHandler) {
        this.pathHandler = pathHandler;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

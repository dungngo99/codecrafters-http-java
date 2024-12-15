package dto;

import handler.PathHandler;

import java.util.Map;

public class RequestContextDto {
    private String[] targets;
    private PathHandler pathHandler;
    private Map<String, String> headers;

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
}

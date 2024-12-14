package dto;

import handler.PathHandler;

public class PathDto {
    private String[] targets;
    private PathHandler pathHandler;

    public PathDto(String[] targets, PathHandler pathHandler) {
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
}

package dto;

import handler.PathHandler;

import java.util.HashMap;
import java.util.Map;

public class NodeDto {
    private String name;
    private Map<String, NodeDto> map;
    private PathHandler pathHandler;
    private boolean isTerminal;

    public NodeDto(String name) {
        this.name = name;
        this.map = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, NodeDto> getMap() {
        return map;
    }

    public void setMap(Map<String, NodeDto> map) {
        this.map = map;
    }

    public PathHandler getPathHandler() {
        return pathHandler;
    }

    public void setPathHandler(PathHandler pathHandler) {
        this.pathHandler = pathHandler;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    public void setTerminal(boolean terminal) {
        isTerminal = terminal;
    }
}

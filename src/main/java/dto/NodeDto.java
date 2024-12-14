package dto;

import java.util.Map;

public class NodeDto {
    private String path;
    private Map<String, NodeDto> map;

    public NodeDto(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, NodeDto> getMap() {
        return map;
    }

    public void setMap(Map<String, NodeDto> map) {
        this.map = map;
    }
}

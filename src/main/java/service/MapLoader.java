package service;

import constants.OutputConstants;
import dto.NodeDto;

import java.util.HashMap;
import java.util.Map;

public class MapLoader {

    public static final Map<String, NodeDto> MAP = new HashMap<>();

    public static void load() {
        MAP.put(OutputConstants.ROOT, new NodeDto(OutputConstants.ROOT));
    }
}

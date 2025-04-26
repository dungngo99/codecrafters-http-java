package service;

import constants.Constants;
import dto.NodeDto;
import dto.RequestContextDto;
import enums.Endpoint;
import handler.PathHandler;
import handler.impl.*;

import java.util.Arrays;
import java.util.Map;

public class HttpPath {
    public static NodeDto ROOT;
    public static NodeDto NOT_FOUND;

    public static void registerPath() {
        new NotFoundHandler().registerPath();
        new RootHandler().registerPath();
        new EchoHandler().registerPath();
        new UserAgentHandler().registerPath();
        new FileHandler().registerPath();
    }

    public static void loadEndpoint(NodeDto[] paths) {
        if (paths == null || paths.length == 0) {
            return;
        }

        load0(ROOT, paths);
    }

    private static void load0(NodeDto root, NodeDto[] paths) {
        if (paths == null || paths.length == 0) {
            return;
        }

        NodeDto node = paths[0];
        String name = node.getName();
        Map<String, NodeDto> map = root.getMap();
        NodeDto nextRoot;
        if (map.containsKey(name)) {
            nextRoot = map.get(name);
        } else {
            nextRoot = new NodeDto(node.getName());
            nextRoot.setPathHandler(node.getPathHandler());
            nextRoot.setTerminal(node.isTerminal());
            map.put(name, nextRoot);
        }

        load0(nextRoot, Arrays.copyOfRange(paths, 1, paths.length));
    }

    public static NodeDto[] convert2PathNode(Endpoint path, PathHandler pathHandler) {
        String[] paths = path.getPath().split(Constants.PATH_DELIMITER);
        NodeDto[] nodeDtoArr = new NodeDto[paths.length];
        for (int i=0; i<paths.length; i++) {
            nodeDtoArr[i] = new NodeDto(paths[i]);
        }
        NodeDto lastNodeDto = nodeDtoArr[paths.length-1];
        lastNodeDto.setPathHandler(pathHandler);
        lastNodeDto.setTerminal(path.isTerminal());
        return nodeDtoArr;
    }

    public static RequestContextDto resolvePath(String[] targets) {
        if (targets == null || targets.length == 0) {
            return new RequestContextDto(new String[0], ROOT.getPathHandler());
        }

        return resolvePath0(ROOT, targets);
    }

    private static RequestContextDto resolvePath0(NodeDto root, String[] targets) {
        if (targets == null || root == null) {
            return new RequestContextDto(new String[0], NOT_FOUND.getPathHandler());
        }
        if (targets.length == 0) {
            return new RequestContextDto(new String[0], root.getPathHandler());
        }

        String target = targets[0];
        Map<String, NodeDto> map = root.getMap();
        NodeDto nextRoot = map.getOrDefault(target, null);
        String[] nextTargets = Arrays.copyOfRange(targets, 1, targets.length);
        if (nextRoot != null && nextRoot.isTerminal()) {
            PathHandler pathHandler = nextRoot.getPathHandler();
            return new RequestContextDto(nextTargets, pathHandler);
        }

        return resolvePath0(nextRoot, nextTargets);
    }
}

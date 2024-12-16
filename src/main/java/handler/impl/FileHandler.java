package handler.impl;

import constants.OutputConstants;
import dto.RequestContextDto;
import enums.Endpoint;
import handler.PathHandler;
import service.HttpPath;
import service.SocketServer;
import utils.HttpUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler implements PathHandler {
    @Override
    public void registerPath() {
        HttpPath.loadEndpoint(HttpPath.convert2PathNode(Endpoint.FILE, this));
        HttpPath.loadEndpoint(HttpPath.convert2PathNode(Endpoint.FILE_STR, this));
    }

    @Override
    public String process(RequestContextDto contextDto) {
        if (contextDto == null) {
            return OutputConstants.EMPTY_STRING;
        }
        String[] targets = contextDto.getTargets();
        String fileName = targets[OutputConstants.FILE_NAME_INDEX];
        String absPathDir = SocketServer.getSystemProperty(OutputConstants.APP_ARGS_DIRECTORY_KEY);
        Path path = Paths.get(absPathDir, fileName);
        File file = path.toFile();

        if (!file.exists() || file.isDirectory()) {
            return HttpUtils.getNotFoundStatus();
        }

        try {
            FileInputStream is = new FileInputStream(file);
            byte[] bytes = is.readAllBytes();
            return HttpUtils.getResponseWithBodyAsByteStream(bytes);
        } catch (IOException e) {
            System.out.println("failed to read from file due to " + e.getMessage());
        }

        return HttpUtils.getNotFoundStatus();
    }
}

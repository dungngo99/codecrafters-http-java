package handler.impl;

import constants.OutputConstants;
import dto.RequestContextDto;
import enums.Endpoint;
import enums.HttpMethod;
import handler.PathHandler;
import service.HttpPath;
import service.SocketServer;
import utils.HttpUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

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
        String requestMethod = contextDto.getRequestMethod();
        if (Objects.equals(requestMethod, HttpMethod.GET.name())) {
            return handleGet(contextDto);
        } else if (Objects.equals(requestMethod, HttpMethod.POST.name())) {
            return handlePost(contextDto);
        }
        return HttpUtils.getNotFoundStatus();
    }

    private String handleGet(RequestContextDto contextDto) {
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

    private String handlePost(RequestContextDto contextDto) {
        String body = contextDto.getBody();
        if (body == null) {
            return HttpUtils.getNotFoundStatus();
        }

        String[] targets = contextDto.getTargets();
        String fileName = targets[OutputConstants.FILE_NAME_INDEX];
        String absPathDir = SocketServer.getSystemProperty(OutputConstants.APP_ARGS_DIRECTORY_KEY);
        Path path = Paths.get(absPathDir, fileName);
        File file = path.toFile();

        try {
            FileOutputStream os = new FileOutputStream(file);
            os.write(body.getBytes(StandardCharsets.UTF_8));
            return HttpUtils.getCreatedStatus();
        } catch (IOException e) {
            System.out.println("failed to read from file due to " + e.getMessage());
        }

        return HttpUtils.getNotFoundStatus();
    }
}

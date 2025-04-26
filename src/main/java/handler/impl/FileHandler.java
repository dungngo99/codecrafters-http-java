package handler.impl;

import constants.Constants;
import dto.RequestContextDto;
import dto.ResponseDto;
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
    public ResponseDto process(RequestContextDto contextDto) {
        if (contextDto == null) {
            return new ResponseDto();
        }
        String requestMethod = contextDto.getRequestMethod();
        if (Objects.equals(requestMethod, HttpMethod.GET.name())) {
            return handleGet(contextDto);
        } else if (Objects.equals(requestMethod, HttpMethod.POST.name())) {
            return handlePost(contextDto);
        }
        return HttpUtils.getNotFoundResponse();
    }

    private ResponseDto handleGet(RequestContextDto contextDto) {
        String[] targets = contextDto.getTargets();
        String fileName = targets[Constants.FILE_NAME_INDEX];
        String absPathDir = SocketServer.getSystemProperty(Constants.APP_ARGS_DIRECTORY_KEY);
        Path path = Paths.get(absPathDir, fileName);
        File file = path.toFile();

        if (!file.exists() || file.isDirectory()) {
            return HttpUtils.getNotFoundResponse();
        }

        try {
            FileInputStream is = new FileInputStream(file);
            byte[] bytes = is.readAllBytes();
            return HttpUtils.getResponseWithBodyAsByteStream(bytes);
        } catch (IOException e) {
            System.out.println("failed to read from file due to " + e.getMessage());
        }

        return HttpUtils.getNotFoundResponse();
    }

    private ResponseDto handlePost(RequestContextDto contextDto) {
        String body = contextDto.getBody();
        if (body == null) {
            return HttpUtils.getNotFoundResponse();
        }

        String[] targets = contextDto.getTargets();
        String fileName = targets[Constants.FILE_NAME_INDEX];
        String absPathDir = SocketServer.getSystemProperty(Constants.APP_ARGS_DIRECTORY_KEY);
        Path path = Paths.get(absPathDir, fileName);
        File file = path.toFile();

        try {
            FileOutputStream os = new FileOutputStream(file);
            os.write(body.getBytes(StandardCharsets.UTF_8));
            return HttpUtils.getCreatedResponse();
        } catch (IOException e) {
            System.out.println("failed to read from file due to " + e.getMessage());
        }

        return HttpUtils.getNotFoundResponse();
    }
}

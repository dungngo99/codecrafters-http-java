package handler.impl;

import dto.RequestContextDto;
import dto.ResponseDto;
import enums.Endpoint;
import handler.PathHandler;
import service.HttpPath;
import utils.HttpUtils;

public class EchoHandler implements PathHandler {
    @Override
    public void registerPath() {
        HttpPath.loadEndpoint(HttpPath.convert2PathNode(Endpoint.ECHO, this));
        HttpPath.loadEndpoint(HttpPath.convert2PathNode(Endpoint.ECHO_STR, this));
    }

    @Override
    public ResponseDto process(RequestContextDto contextDto) {
        String[] targets = contextDto.getTargets();
        if (targets == null || targets.length == 0) {
            return new ResponseDto();
        }
        return HttpUtils.getResponseWithBodyAsPlainText(targets[0]);
    }
}

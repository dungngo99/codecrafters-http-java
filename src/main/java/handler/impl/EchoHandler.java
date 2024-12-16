package handler.impl;

import constants.OutputConstants;
import dto.RequestContextDto;
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
    public String process(RequestContextDto contextDto) {
        String[] targets = contextDto.getTargets();
        if (targets == null || targets.length == 0) {
            return OutputConstants.EMPTY_STRING;
        }
        return HttpUtils.getResponseWithBodyAsPlainText(targets[0]);
    }
}

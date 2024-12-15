package handler.impl;

import constants.OutputConstants;
import dto.RequestContextDto;
import enums.Endpoint;
import handler.PathHandler;
import service.HttpPath;
import utils.HttpUtils;

public class UserAgentHandler implements PathHandler {
    @Override
    public void registerPath() {
        HttpPath.loadPath(HttpPath.convert2PathNode(Endpoint.USER_AGENT, this));
    }

    @Override
    public String process(RequestContextDto contextDto) {
        if (contextDto == null || contextDto.getHeaders().isEmpty()) {
            return OutputConstants.EMPTY_STRING;
        }
        String userAgent = contextDto.getHeaders().get(OutputConstants.USER_AGENT);
        return HttpUtils.getResponseWithBodyAsPlainText(userAgent);
    }
}
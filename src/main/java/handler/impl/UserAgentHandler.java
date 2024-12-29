package handler.impl;

import constants.OutputConstants;
import dto.RequestContextDto;
import dto.ResponseDto;
import enums.Endpoint;
import handler.PathHandler;
import service.HttpPath;
import utils.HttpUtils;

public class UserAgentHandler implements PathHandler {
    @Override
    public void registerPath() {
        HttpPath.loadEndpoint(HttpPath.convert2PathNode(Endpoint.USER_AGENT, this));
    }

    @Override
    public ResponseDto process(RequestContextDto contextDto) {
        if (contextDto == null || contextDto.getHeaders().isEmpty()) {
            return new ResponseDto();
        }
        String userAgent = contextDto.getHeaders().get(OutputConstants.USER_AGENT);
        return HttpUtils.getResponseWithBodyAsPlainText(userAgent);
    }
}

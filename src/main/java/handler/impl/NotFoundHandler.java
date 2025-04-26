package handler.impl;

import constants.Constants;
import dto.NodeDto;
import dto.RequestContextDto;
import dto.ResponseDto;
import enums.Endpoint;
import handler.PathHandler;
import service.HttpPath;
import utils.HttpUtils;

public class NotFoundHandler implements PathHandler {
    @Override
    public void registerPath() {
        NodeDto nodeDto = new NodeDto(Constants.NOT_FOUND);
        nodeDto.setPathHandler(this);
        nodeDto.setTerminal(Endpoint.NOT_FOUND.isTerminal());
        HttpPath.NOT_FOUND = nodeDto;
    }

    @Override
    public ResponseDto process(RequestContextDto contextDto) {
        return HttpUtils.getNotFoundResponse();
    }
}

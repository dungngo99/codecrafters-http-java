package handler.impl;

import constants.OutputConstants;
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
        NodeDto nodeDto = new NodeDto(OutputConstants.NOT_FOUND);
        nodeDto.setPathHandler(this);
        nodeDto.setTerminal(Endpoint.NOT_FOUND.isTerminal());
        HttpPath.NOT_FOUND = nodeDto;
    }

    @Override
    public ResponseDto process(RequestContextDto contextDto) {
        return HttpUtils.getNotFoundResponse();
    }
}

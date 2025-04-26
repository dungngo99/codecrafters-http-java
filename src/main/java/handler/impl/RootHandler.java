package handler.impl;

import constants.Constants;
import dto.NodeDto;
import dto.RequestContextDto;
import dto.ResponseDto;
import enums.Endpoint;
import handler.PathHandler;
import service.HttpPath;
import utils.HttpUtils;

public class RootHandler implements PathHandler {
    @Override
    public void registerPath() {
        NodeDto nodeDto = new NodeDto(Constants.ROOT);
        nodeDto.setPathHandler(this);
        nodeDto.setTerminal(Endpoint.ROOT.isTerminal());
        HttpPath.ROOT = nodeDto;
    }

    @Override
    public ResponseDto process(RequestContextDto contextDto) {
        return HttpUtils.getOKResponse();
    }
}

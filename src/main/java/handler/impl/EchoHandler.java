package handler.impl;

import constants.OutputConstants;
import enums.Endpoint;
import handler.PathHandler;
import service.HttpPath;
import utils.HttpUtils;

public class EchoHandler implements PathHandler {
    @Override
    public void registerPath() {
        HttpPath.loadPath(HttpPath.convert2PathNode(Endpoint.ECHO, this));
        HttpPath.loadPath(HttpPath.convert2PathNode(Endpoint.ECHO_STR, this));
    }

    @Override
    public String process(String[] targets) {
        if (targets == null || targets.length == 0) {
            return OutputConstants.EMPTY_STRING;
        }
        return HttpUtils.getPlainText(targets[0]);
    }
}

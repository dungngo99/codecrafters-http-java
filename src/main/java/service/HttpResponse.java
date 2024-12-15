package service;

import constants.OutputConstants;
import dto.RequestContextDto;
import dto.RequestDto;
import handler.PathHandler;

public class HttpResponse {

    public static String process(RequestDto requestDto) {
        String target = requestDto.getTargetMethod();
        if (target == null || target.isEmpty()) {
            return OutputConstants.EMPTY_STRING;
        }

        String[] targets = target.split(OutputConstants.PATH_DELIMITER);
        RequestContextDto contextDto = HttpPath.resolvePath(targets);
        contextDto.setHeaders(requestDto.getHeaders());

        PathHandler pathHandler = contextDto.getPathHandler();
        return pathHandler.process(contextDto);
    }
}

package service;

import constants.OutputConstants;
import dto.PathDto;
import dto.RequestDto;

public class HttpResponse {

    public static String process(RequestDto requestDto) {
        String target = requestDto.getTargetMethod();
        if (target == null || target.isEmpty()) {
            return OutputConstants.EMPTY_STRING;
        }
        String[] targets = target.split(OutputConstants.PATH_DELIMITER);
        PathDto pathDto = HttpPath.resolvePath(targets);
        return pathDto.getPathHandler().process(pathDto.getTargets());
    }
}

package utils;

import constants.OutputConstants;
import dto.RequestDto;

public class HttpProcessUtils {

    public static String process(RequestDto requestDto) {
        String target = requestDto.getTargetMethod();
        if (target == null || target.isEmpty()) {
            return HttpUtils.getNotFoundStatus();
        }
        String[] targets = target.split(OutputConstants.PATH_DELIMITER);
        if (targets.length == 0) {
            return HttpUtils.getOKStatus();
        }
        return HttpUtils.getNotFoundStatus();
    }
}

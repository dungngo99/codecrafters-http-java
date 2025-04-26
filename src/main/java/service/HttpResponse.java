package service;

import constants.Constants;
import dto.RequestContextDto;
import dto.RequestDto;
import dto.ResponseDto;
import handler.PathHandler;

import java.util.Map;
import java.util.Objects;

public class HttpResponse {

    public static ResponseDto process(RequestDto requestDto) {
        String target = requestDto.getTargetMethod();
        if (target == null || target.isEmpty()) {
            throw new RuntimeException("failed to process http request due to invalid target");
        }

        String[] targets = target.split(Constants.PATH_DELIMITER);
        RequestContextDto contextDto = HttpPath.resolvePath(targets);
        contextDto.setHeaders(requestDto.getHeaders());
        contextDto.setRequestMethod(requestDto.getHttpMethod());
        contextDto.setBody(requestDto.getBody());

        PathHandler pathHandler = contextDto.getPathHandler();
        return pathHandler.process(contextDto);
    }

    public static void fillResponseHeaders(RequestDto requestDto, ResponseDto responseDto) {
        Map<String, String> requestHeaders = requestDto.getHeaders();
        Map<String, String> responseHeaders = responseDto.getHeaders();
        if (Objects.isNull(requestHeaders) || Objects.isNull(responseHeaders)) {
            return;
        }

        if (requestHeaders.containsKey(Constants.ACCEPT_ENCODING)) {
            String compressScheme = requestHeaders.get(Constants.ACCEPT_ENCODING);
            responseHeaders.put(Constants.CONTENT_ENCODING, compressScheme);
        }
    }
}

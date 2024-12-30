package service;

import constants.OutputConstants;
import dto.RequestContextDto;
import dto.RequestDto;
import dto.ResponseDto;
import handler.PathHandler;
import utils.HttpUtils;

import java.util.List;
import java.util.Map;

public class HttpResponse {

    public static String process(RequestDto requestDto) {
        String target = requestDto.getTargetMethod();
        if (target == null || target.isEmpty()) {
            return OutputConstants.EMPTY_STRING;
        }

        String[] targets = target.split(OutputConstants.PATH_DELIMITER);
        RequestContextDto contextDto = HttpPath.resolvePath(targets);
        contextDto.setHeaders(requestDto.getHeaders());
        contextDto.setRequestMethod(requestDto.getHttpMethod());
        contextDto.setBody(requestDto.getBody());

        PathHandler pathHandler = contextDto.getPathHandler();
        ResponseDto responseDto = pathHandler.process(contextDto);

        fillResponseHeaders(requestDto, responseDto);
        return convertToString(responseDto);
    }

    private static void fillResponseHeaders(RequestDto requestDto, ResponseDto responseDto) {
        Map<String, String> requestHeaders = requestDto.getHeaders();
        Map<String, String> responseHeaders = responseDto.getHeaders();

        if (requestHeaders.containsKey(OutputConstants.ACCEPT_ENCODING)) {
            String compressScheme = requestHeaders.get(OutputConstants.ACCEPT_ENCODING);
            responseHeaders.put(OutputConstants.CONTENT_ENCODING, compressScheme);
        }
    }

    private static String convertToString(ResponseDto responseDto) {
        String headers = OutputConstants.EMPTY_STRING;
        Map<String, String> headerMap = responseDto.getHeaders();
        if (headerMap != null && !headerMap.isEmpty()) {
            headers = HttpUtils.convertFromHeaders(headerMap);
        }
        List<String> list = List.of(responseDto.getStatusLine(), headers, responseDto.getBody());
        return HttpUtils.fromList(list);
    }
}

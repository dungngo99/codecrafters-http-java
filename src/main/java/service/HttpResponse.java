package service;

import constants.OutputConstants;
import dto.RequestContextDto;
import dto.RequestDto;
import dto.ResponseDto;
import enums.CompressScheme;
import handler.PathHandler;
import utils.HttpUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpResponse {

    public static byte[] process(RequestDto requestDto) {
        String target = requestDto.getTargetMethod();
        if (target == null || target.isEmpty()) {
            return new byte[0];
        }

        String[] targets = target.split(OutputConstants.PATH_DELIMITER);
        RequestContextDto contextDto = HttpPath.resolvePath(targets);
        contextDto.setHeaders(requestDto.getHeaders());
        contextDto.setRequestMethod(requestDto.getHttpMethod());
        contextDto.setBody(requestDto.getBody());

        PathHandler pathHandler = contextDto.getPathHandler();
        ResponseDto responseDto = pathHandler.process(contextDto);

        fillResponseHeaders(requestDto, responseDto);
        return convertToByteStream(responseDto);
    }

    private static void fillResponseHeaders(RequestDto requestDto, ResponseDto responseDto) {
        Map<String, String> requestHeaders = requestDto.getHeaders();
        Map<String, String> responseHeaders = responseDto.getHeaders();

        if (requestHeaders.containsKey(OutputConstants.ACCEPT_ENCODING)) {
            String compressScheme = requestHeaders.get(OutputConstants.ACCEPT_ENCODING);
            responseHeaders.put(OutputConstants.CONTENT_ENCODING, compressScheme);
        }
    }

    private static byte[] convertToByteStream(ResponseDto responseDto) {
        Map<String, String> headerMap = responseDto.getHeaders();
        String compressionScheme = headerMap.get(OutputConstants.CONTENT_ENCODING);

        if (CompressScheme.GZIP.name().equalsIgnoreCase(compressionScheme)) {
            byte[] var1 = (responseDto.getStatusLine() + OutputConstants.CRLF).getBytes(StandardCharsets.UTF_8);
            byte[] var3 = HttpUtils.gzipCompress(responseDto.getBody());

            headerMap.put(OutputConstants.CONTENT_LENGTH, String.valueOf(var3.length));
            String headers = HttpUtils.convertFromHeaders(headerMap);
            byte[] var2 = (headers + OutputConstants.CRLF).getBytes(StandardCharsets.UTF_8);

            return HttpUtils.concatTripleBytes(var1, var2, var3);
        } else {
            String headers = OutputConstants.EMPTY_STRING;
            if (!headerMap.isEmpty()) {
                headers = HttpUtils.convertFromHeaders(headerMap);
            }
            List<String> list = List.of(responseDto.getStatusLine(), headers, responseDto.getBody());
            return HttpUtils.fromList(list).getBytes(StandardCharsets.UTF_8);
        }
    }
}

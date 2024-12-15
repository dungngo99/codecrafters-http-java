package handler;

import dto.RequestContextDto;

public interface PathHandler {
    void registerPath();
    String process(RequestContextDto contextDto);
}

package handler;

import dto.RequestContextDto;
import dto.ResponseDto;

public interface PathHandler {
    void registerPath();
    ResponseDto process(RequestContextDto contextDto);
}

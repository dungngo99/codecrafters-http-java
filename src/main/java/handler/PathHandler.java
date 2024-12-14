package handler;

public interface PathHandler {
    void registerPath();
    String process(String[] targets);
}

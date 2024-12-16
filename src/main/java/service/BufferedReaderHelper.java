package service;

import constants.OutputConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class BufferedReaderHelper extends BufferedReader {

    private int cursor;
    private final int size;
    private final char[] buffer;

    /**
     * A helper class to facilitate reading stream from HTTP request
     *
     * Assume that total chars read from BufferedReader will be less than or equal to OutputConstants.INPUT_STREAM_READER_BUFFER_SIZE
     * Therefore the implementation will be more simple
     * @param in reader
     * @throws IOException after filling buffer
     */
    public BufferedReaderHelper(Reader in) throws IOException {
        super(in);
        this.cursor = 0;
        this.buffer = new char[OutputConstants.INPUT_STREAM_READER_BUFFER_SIZE];
        this.size = in.read(this.buffer);
    }

    public boolean hasNext() {
        return this.cursor < this.size;
    }

    public boolean canRead() {
        return this.size > 0;
    }

    public char peek() {
        return this.buffer[this.cursor];
    }

    public void skipNChars(int N) {
        this.cursor+=N;
    }

    public char[] readNextChars() {
        int start = this.cursor;
        int end = this.cursor;
        while (hasNext()) {
            char c = peek();
            if (c == OutputConstants.CR) {
                end = this.cursor;
                skipNChars(OutputConstants.CRLF_LENGTH);
                break;
            }
            skipNChars(OutputConstants.INPUT_STREAM_READER_INCREMENT_CURSOR);
        }
        return Arrays.copyOfRange(this.buffer, start, end);
    }

    public char[] readNChars(int N) {
        char[] ans = Arrays.copyOfRange(this.buffer, this.cursor, this.cursor+N);
        skipNChars(N);
        return ans;
    }
}

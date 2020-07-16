package nl.sander.jsontoy2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.function.Supplier;

/**
 * implements lowest level operations on inputstream.
 */
public class Lexer implements AutoCloseable {
    protected InputStream inputStream;
    protected final ByteBuf characterBuffer = new ByteBuf();
    protected byte current;
    protected int charCount = 0;

    public Lexer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    void advance() {
        charCount++;
        try {
            current = (byte) inputStream.read();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    void skipWhitespace() {
        try {
            while (current > -1 && Character.isWhitespace(current)) {
                current = (byte) inputStream.read();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    void eatUntil(char... until) {
        while (current > -1 && (!contains(until, current) | Character.isWhitespace(current))) {
            advance();
        }
        advance();
    }

    private boolean contains(char[] characters, byte c) {
        return Arrays.binarySearch(characters, (char) c) > -1;
    }

    void expect(Supplier<JsonParseException> exceptionSupplier, char... word) {
        int increment = 0;

        while (current > -1 && increment < word.length) {
            if (current != word[increment++]) {
                throw exceptionSupplier.get();
            } else {
                advance();
            }
        }
    }

    public void close() {
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }
}

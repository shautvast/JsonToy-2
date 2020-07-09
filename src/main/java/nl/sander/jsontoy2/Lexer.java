package nl.sander.jsontoy2;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

/**
 * implements lowest level operations on inputstream.
 */
public class Lexer {
    protected final InputStream inputStream;
    protected final ByteBuf characterBuffer = new ByteBuf();
    protected byte current;

    public Lexer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    void advance() {
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
            if (current == -1) {
                throw new JsonParseException("end of source reached");
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    void eatUntil(char until) {
        while (current > -1 && (current != until | Character.isWhitespace(current))) {
            advance();
        }
        advance();
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
}

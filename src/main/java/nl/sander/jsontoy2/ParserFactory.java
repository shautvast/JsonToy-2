package nl.sander.jsontoy2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Keeps a Threadlocal parser that can be (re)used by clients.
 * <p>
 * The parser is not kept indefinitely, but garbage collected when the JVM considers necessary.
 */
public class ParserFactory {

    private final static ThreadLocal<SoftReference<Parser>> PARSERS = new ThreadLocal<>();

    private ParserFactory() {
    }

    static Parser getParser(String jsonString) {
        return getParser(new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8)));
    }

    static Parser getParser(InputStream inputStream) {
        Objects.requireNonNull(inputStream, "File not found");
        Parser parser;
        SoftReference<Parser> parserReference = PARSERS.get();
        if (parserReference == null || (parser = parserReference.get()) == null) {
            parser = new Parser(inputStream);
            parserReference = new SoftReference<>(parser);
            PARSERS.set(parserReference);
        } else {
            parser.init(inputStream);
        }
        return parser;
    }
}

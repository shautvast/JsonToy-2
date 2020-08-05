package nl.sander.jsontoy2;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * public api
 */
public class JsonReader {


    private final static ThreadLocal<SoftReference<Parser>> PARSERS = new ThreadLocal<>();


    /**
     * reads a value from a stream for a type that is not known beforehand
     *
     * @param inputStream the underlying stream to read
     * @return an Object with runtime type as follows:
     * "null" => null
     * "true"/"false" => Boolean
     * integral number  => Integer //TODO Long?
     * floating point number => Double
     * string => String
     * object => HashMap
     * array => List
     */
    public static Object read(InputStream inputStream) {
        final InputStream in = ensureBufferedStream(inputStream);
        try (Parser parser = getParser(in)) {
            return read(parser);
        }
    }

    /**
     * Reads a value from a string for a type that is not known beforehand
     *
     * @param jsonString the Json String to read
     * @return @see read(InputStream stream)
     */
    public static Object read(String jsonString) {
        return read(getParser(jsonString));
    }

    /**
     * Reads a value from a stream for the given type
     *
     * @param type        The class for the type that is needed
     * @param inputStream The stream to read
     * @param <T>         the type that is needed
     * @return Object the specified type
     */
    public static <T> T read(Class<T> type, InputStream inputStream) {
        Parser parser = getParser(inputStream);
        T value = read(type, parser);
        parser.close();
        return value;
    }

    /**
     * Reads a value from a stream for the given type
     *
     * @param type       The class for the type that is needed
     * @param jsonString The String to read
     * @param <T>        the type that is needed
     * @return Object the specified type
     */
    public static <T> T read(Class<T> type, String jsonString) {
        return read(type, getParser(jsonString));
    }

    private static Parser getParser(String jsonString) {
        return getParser(new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8)));
    }

    private static Parser getParser(InputStream inputStream) {
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

    static Object read(Parser parser) {
        return parser.parseAny();
    }

    @SuppressWarnings("unchecked")
    public static <T> T read(Class<T> type, Parser parser) {
        return (T) ReaderFactory.getReader(type).read(parser);
//        class.cast() does not work well for primitives;
    }

    @SuppressWarnings("unused")
    public static Map<?, ?> readJavaObject(Class<?> type, Parser parser) {
        return parser.parseObject(type);
    }


    private static InputStream ensureBufferedStream(InputStream inputStream) {
        if (inputStream instanceof BufferedInputStream) {
            return inputStream;
        } else {
            return new BufferedInputStream(inputStream);
        }
    }


}

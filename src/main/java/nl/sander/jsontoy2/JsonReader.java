package nl.sander.jsontoy2;

import nl.sander.jsontoy2.readers.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

/**
 * public api
 */
public class JsonReader {
    private static final ConcurrentMap<Class<?>, Supplier<JsonValueReader<?>>> readSuppliers = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class<?>, JsonValueReader<?>> readers = new ConcurrentHashMap<>();

    private final static ThreadLocal<SoftReference<Parser>> PARSERS = new ThreadLocal<>();

    static {
        registerPrimitiveTypeReaders();
    }

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
        InputStream in = ensureBuffered(inputStream);
        try (Parser parser = getParser(in)) {
            return read(parser);
        }
    }

    private static InputStream ensureBuffered(InputStream inputStream) {
        if (inputStream instanceof BufferedInputStream) {
            return inputStream;
        } else {
            return new BufferedInputStream(inputStream);
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

    @SuppressWarnings("unchecked")
    private static <T> T read(Class<T> type, Parser parser) {
        return (T) getReader(type).read(parser);
//        class.cast() does not work well for primitives;
    }

    private static <T> JsonValueReader<?> getReader(Class<T> type) {
        return readers.computeIfAbsent(type, k -> readSuppliers.get(k).get());
    }

    private static <T> void register(Class<T> type, Supplier<JsonValueReader<?>> objectReader) {
        readSuppliers.put(type, objectReader);
    }

    private static void registerPrimitiveTypeReaders() {
        register(Boolean.class, BooleanReader::new);
        register(boolean.class, BooleanReader::new);
        register(Integer.class, IntegerReader::new);
        register(int.class, IntegerReader::new);
        register(Long.class, LongReader::new);
        register(long.class, LongReader::new);
        register(Byte.class, ByteReader::new);
        register(byte.class, ByteReader::new);
        register(Short.class, ShortReader::new);
        register(short.class, ShortReader::new);
        register(Double.class, DoubleReader::new);
        register(double.class, DoubleReader::new);
        register(Float.class, FloatReader::new);
        register(float.class, FloatReader::new);
        register(Date.class, DateReader::new);
        register(Character.class, CharReader::new);
        register(char.class, CharReader::new);
        register(String.class, StringReader::new);
        register(LocalDateTime.class, LocalDateTimeReader::new);
        register(List.class, ListReader::new);
        register(Map.class, MapReader::new);
    }
}

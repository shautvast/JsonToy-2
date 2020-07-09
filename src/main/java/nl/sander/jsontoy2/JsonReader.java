package nl.sander.jsontoy2;

import nl.sander.jsontoy2.readers.*;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * public facade
 */
public class JsonReader {
    private static final ConcurrentMap<Class<?>, JsonValueReader<?>> readers = new ConcurrentHashMap<>();

    /**
     * reads a value for a type that is not known beforehand
     *
     * @param stream the underlying stream to read
     * @return an Object with runtime type as follows:
     * "null" => null
     * "true"/"false" => Boolean
     * integral number  => Integer //TODO Long?
     * floating point number => Double
     * string => String
     * object => HashMap
     * array => List
     */
    public static Object read(InputStream stream) {
        return read(new Parser(stream));
    }

    public static Object read(String jsonString) {
        return read(new Parser(jsonString));
    }

    static Object read(Parser parser) {
        return parser.parseAny();
    }

    public static <T> T read(Class<T> type, InputStream reader) {
        return read(type, new Parser(reader));
    }

    public static <T> T read(Class<T> type, String jsonString) {
        return read(type, new Parser(jsonString));
    }

    @SuppressWarnings("unchecked")
    static <T> T read(Class<T> type, Parser parser) {
        return (T) getReader(type).read(parser);
//        class.cast() does not work for primitives;
    }

    private static <T> JsonValueReader<?> getReader(Class<T> type) {
        return readers.get(type);
    }

    static <T> void register(Class<T> type, JsonValueReader<T> objectReader) {
        readers.put(type, objectReader);
    }

    static {
        register(Boolean.class, new BooleanReader());
        register(boolean.class, new BooleanReader());
        register(Integer.class, new IntegerReader());
        register(int.class, new IntegerReader());
        register(Long.class, new LongReader());
        register(long.class, new LongReader());
        register(Byte.class, new ByteReader());
        register(byte.class, new ByteReader());
        register(Short.class, new ShortReader());
        register(short.class, new ShortReader());
        register(Double.class, new DoubleReader());
        register(double.class, new DoubleReader());
        register(Float.class, new FloatReader());
        register(float.class, new FloatReader());
        register(Date.class, new DateReader());
        register(Character.class, new CharReader());
        register(char.class, new CharReader());
        register(String.class, new StringReader());
        register(LocalDateTime.class, new LocalDateTimeReader());
        register(List.class, new ListReader());
        register(Map.class, new MapReader());
    }
}

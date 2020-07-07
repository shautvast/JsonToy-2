package nl.sander.jsontoy2;

import nl.sander.jsontoy2.readers.*;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * public facade
 */
public class JsonReader {
    private static final ConcurrentMap<Class<?>, JsonObjectReader<?>> readers = new ConcurrentHashMap<>();

    public static <T> T read(Class<T> type, InputStream reader) {
        return read(type, new IoReader(reader));
    }

    public static <T> T read(Class<T> type, String jsonString) {
        return read(type, new IoReader(jsonString));
    }

    @SuppressWarnings("unchecked")
    public static <T> T read(Class<T> type, IoReader ioReader) {
        return (T) getReader(type).read(ioReader);
//        class.cast() does not work for primitives;
    }

    private static <T> JsonObjectReader<?> getReader(Class<T> type) {
        return readers.get(type);
    }

    public static <T> void register(Class<T> type, JsonObjectReader<T> objectReader) {
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
    }
}

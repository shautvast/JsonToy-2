package nl.sander.jsontoy2;

import nl.sander.jsontoy2.readers.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

public class ReaderFactory {
    static final ConcurrentMap<Class<?>, Supplier<JsonValueReader<?>>> readerSuppliers = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class<?>, JsonValueReader<?>> readers = new ConcurrentHashMap<>();
    private final static MapReader MAPREADER = new MapReader();
    private final static ListReader LISTREADER = new ListReader();

    static {
        registerPrimitiveTypeReaders();
    }

    private ReaderFactory() {
    }

    public static <T> void registerCustomReader(Class<T> type, JsonValueReader<T> reader) {
        readers.put(type, reader);
    }

    static <T> void register(Class<T> type, Supplier<JsonValueReader<?>> objectReader) {
        readerSuppliers.put(type, objectReader);
    }

    static <T> JsonValueReader<?> getReader(Class<T> type) {
        if (Map.class.isAssignableFrom(type)) {
            return MAPREADER;
        } else if (List.class.isAssignableFrom(type) || Set.class.isAssignableFrom(type) || type.isArray()) {
            return LISTREADER;
        } else {
            return readers.computeIfAbsent(type, k -> {
                Supplier<JsonValueReader<?>> jsonValueReaderSupplier = readerSuppliers.get(k);

                return Optional.ofNullable(jsonValueReaderSupplier)
                        .orElseGet(() -> () -> JavaObjectReaderFactory.createReaderInstance(type))
                        .get();
            });
        }
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
    }
}

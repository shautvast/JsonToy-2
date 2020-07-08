package nl.sander.jsontoy2;

import java.io.Reader;

public interface JsonValueReader<T> {
    T read(IoReader ioReader);
}

package nl.sander.jsontoy2;

import java.io.Reader;

public interface JsonObjectReader<T> {
    T read(IoReader ioReader);
}

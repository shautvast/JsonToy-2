package nl.sander.jsontoy2;

public interface JsonValueReader<T> {
    T read(Parser parser);
}

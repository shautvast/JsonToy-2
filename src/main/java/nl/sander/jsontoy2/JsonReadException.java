package nl.sander.jsontoy2;

public class JsonReadException extends RuntimeException {

    public JsonReadException(String message) {
        super(message);
    }

    public JsonReadException(Throwable e) {
        super(e);
    }
}

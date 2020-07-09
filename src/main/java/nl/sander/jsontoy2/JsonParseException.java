package nl.sander.jsontoy2;

public class JsonParseException extends RuntimeException {

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(Throwable e) {
        super(e);
    }
}

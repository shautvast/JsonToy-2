package nl.sander.jsontoy2.javassist;

public class ClassCreationException extends RuntimeException {
    public ClassCreationException(Throwable e) {
        super(e);
    }

    public ClassCreationException(String message) {
        super(message);
    }
}

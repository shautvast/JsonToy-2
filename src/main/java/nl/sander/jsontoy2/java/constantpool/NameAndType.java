package nl.sander.jsontoy2.java.constantpool;

public class NameAndType {
    private final String name;
    private final String type;

    public NameAndType(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}

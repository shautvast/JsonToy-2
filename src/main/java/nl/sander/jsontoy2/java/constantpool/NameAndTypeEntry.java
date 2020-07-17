package nl.sander.jsontoy2.java.constantpool;

public class NameAndTypeEntry extends ConstantPoolEntry {
    private final int nameIndex;
    private final int typeIndex;

    public NameAndTypeEntry(int nameIndex, int typeIndex) {
        this.nameIndex = nameIndex;
        this.typeIndex = typeIndex;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    @Override
    public String toString() {
        return "NameAndTypeEntry{" +
                "nameIndex=" + nameIndex +
                ", typeIndex=" + typeIndex +
                '}';
    }
}

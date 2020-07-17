package nl.sander.jsontoy2.java.constantpool;

public class FieldRefEntry extends ConstantPoolEntry {
    private final int classIndex;
    private final int nameAndTypeIndex;

    public FieldRefEntry(int classIndex, int nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    @Override
    public String toString() {
        return "FieldRefEntry{" +
                "classIndex=" + classIndex +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                '}';
    }
}

package nl.sander.jsontoy2.java.constantpool;

public class MethodRefEntry extends ConstantPoolEntry {
    private final int classIndex;
    private final int nameAndTypeIndex;

    public MethodRefEntry(int classIndex, int nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    @Override
    public String toString() {
        return "MethodRefEntry{" +
                "classIndex=" + classIndex +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                '}';
    }
}

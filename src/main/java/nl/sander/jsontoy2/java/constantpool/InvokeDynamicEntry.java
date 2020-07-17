package nl.sander.jsontoy2.java.constantpool;

public class InvokeDynamicEntry extends ConstantPoolEntry {
    private final int bootstrapMethodAttrIndex;
    private final int nameAndTypeIndex;

    public InvokeDynamicEntry(int bootstrapMethodAttrIndex, int nameAndTypeIndex) {
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public String toString() {
        return "InvokeDynamicEntry{" +
                "bootstrapMethodAttrIndex=" + bootstrapMethodAttrIndex +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                '}';
    }
}

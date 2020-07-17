package nl.sander.jsontoy2.java.constantpool;

public class PackageEntry extends ConstantPoolEntry {
    private final int nameIndex;

    public PackageEntry(int nameIndex) {
        this.nameIndex = nameIndex;
    }
}

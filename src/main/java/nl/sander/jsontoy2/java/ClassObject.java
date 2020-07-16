package nl.sander.jsontoy2.java;

import nl.sander.jsontoy2.java.constantpool.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


public class ClassObject<T> {

    private int constantPoolCount;
    private ConstantPoolEntry[] constantPool;
    private int constantPoolIndex = 0;

    public int getConstantPoolCount() {
        return constantPoolCount;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < constantPoolCount - 1; i++) {
            ConstantPoolEntry entry = constantPool[i];
            builder.append(entry.toString());
            builder.append(String.format("%n"));
        }
        return builder.toString();
    }

    private void add(ConstantPoolEntry entry) {
        constantPool[constantPoolIndex++] = entry;
    }

    public Set<Field> getFields() {
        return Arrays.stream(constantPool)
                .filter(e -> e instanceof FieldRefEntry)
                .map(FieldRefEntry.class::cast)
                .map(f -> {
                    NameAndType nat = getNameAndType(f);
                    return new Field(nat.getName(), nat.getType());
                })
                .collect(Collectors.toSet());
    }

    private NameAndType getNameAndType(FieldRefEntry f) {
        NameAndTypeEntry natEntry = (NameAndTypeEntry) constantPool[f.getNameAndTypeIndex() - 1];
        return new NameAndType(getUtf8(natEntry.getNameIndex()), getUtf8(natEntry.getTypeIndex()));
    }

    private String getUtf8(short index) {
        return ((Utf8Entry) constantPool[index - 1]).getUtf8();
    }

    public static class Builder<T> {

        private final ClassObject<T> classObject = new ClassObject<>();

        public ClassObject<T> build() {
            return classObject;
        }

        public Builder<T> constantPoolCount(int constantPoolCount) {
            classObject.constantPoolCount = constantPoolCount;
            classObject.constantPool = new ConstantPoolEntry[constantPoolCount];
            return this;
        }

        public void constantPoolEntry(String utf8) {
            classObject.add(new Utf8Entry(utf8));
        }

        public void constantPoolEntry(int i) {
            classObject.add(new IntEntry(i));
        }

        public void constantPoolEntry(float f) {
            classObject.add(new FloatEntry(f));
        }

        public void constantPoolEntry(long f) {
            classObject.add(new LongEntry(f));
        }

        public void constantPoolEntry(double d) {
            classObject.add(new DoubleEntry(d));
        }

        public void constantPoolClassEntry(short nameIndex) {
            classObject.add(new ClassEntry(nameIndex));
        }

        public void constantPoolStringEntry(short utf8Index) {
            classObject.add(new StringEntry(utf8Index));
        }

        public void constantPoolFieldRefEntry(short classIndex, short nameAndTypeIndex) {
            classObject.add(new FieldRefEntry(classIndex, nameAndTypeIndex));
        }

        public void constantPoolMethodRefEntry(short classIndex, short nameAndTypeIndex) {
            classObject.add(new MethodRefEntry(classIndex, nameAndTypeIndex));
        }

        public void constantPoolInterfaceMethodRefEntry(short classIndex, short nameAndTypeIndex) {
            classObject.add(new InterfaceMethodRefEntry(classIndex, nameAndTypeIndex));
        }

        public void constantPoolNameAndTypeEntry(short nameIndex, short typeIndex) {
            classObject.add(new NameAndTypeEntry(nameIndex, typeIndex));
        }

        public void constantPoolMethodHandleEntry(short referenceKind, short referenceIndex) {
            classObject.add(new MethodHandleEntry(referenceKind, referenceIndex));
        }

        public void constantPoolMethodTypeEntry(short descriptorIndex) {
            classObject.add(new MethodTypeEntry(descriptorIndex));
        }

        public void constantPoolInvokeDynamicEntry(short bootstrapMethodAttrIndex, short nameAndTypeIndex) {
            classObject.add(new InvokeDynamicEntry(bootstrapMethodAttrIndex, nameAndTypeIndex));
        }
    }
}

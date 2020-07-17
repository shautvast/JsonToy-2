package nl.sander.jsontoy2.java;

import nl.sander.jsontoy2.java.constantpool.ConstantPoolEntry;
import nl.sander.jsontoy2.java.constantpool.Utf8Entry;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


public class ClassObject<T> {

    private ConstantPoolEntry[] constantPool;
    private Info[] fieldInfos;
    private Info[] methodInfos;

    private String getUtf8(int index) {
        return ((Utf8Entry) constantPool[index - 1]).getUtf8();
    }

    public Set<Field> getFields() {
        return Arrays.stream(fieldInfos)
                .map(fi -> new Field(getUtf8(fi.getNameIndex()), getUtf8(fi.getDescriptorIndex())))
                .collect(Collectors.toSet());
    }

    public Set<Method> getMethods() {
        return Arrays.stream(methodInfos)
                .map(mi -> new Method(getUtf8(mi.getNameIndex()), getUtf8(mi.getDescriptorIndex())))
                .collect(Collectors.toSet());
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (ConstantPoolEntry entry : constantPool) {
            builder.append(entry.toString());
            builder.append(String.format("%n"));
        }
        return builder.toString();
    }

    public static class Builder<T> {

        private final ClassObject<T> classObject = new ClassObject<>();
        private int constantPoolIndex = 0;
        private int fieldInfoIndex = 0;
        private int methodInfoIndex = 0;

        public ClassObject<T> build() {
            return classObject;
        }

        public Builder<T> constantPoolCount(int constantPoolCount) {
            classObject.constantPool = new ConstantPoolEntry[constantPoolCount - 1];
            return this;
        }

        void constantPoolEntry(ConstantPoolEntry entry) {
            classObject.constantPool[constantPoolIndex++] = entry;
        }

        public Builder<T> fieldInfoCount(int fieldInfoCount) {
            classObject.fieldInfos = new Info[fieldInfoCount];
            return this;
        }

        public Builder<T> fieldInfo(Info fieldInfo) {
            classObject.fieldInfos[fieldInfoIndex++] = fieldInfo;
            return this;
        }

        public Builder<T> methodInfoCount(int methodInfoCount) {
            classObject.methodInfos = new Info[methodInfoCount];
            return this;
        }

        public Builder<T> methodInfo(Info methodInfo) {
            classObject.methodInfos[methodInfoIndex++] = methodInfo;
            return this;
        }
    }
}

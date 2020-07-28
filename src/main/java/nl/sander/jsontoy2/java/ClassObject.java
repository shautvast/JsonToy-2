package nl.sander.jsontoy2.java;

import nl.sander.jsontoy2.java.constantpool.ConstantPoolEntry;
import nl.sander.jsontoy2.java.constantpool.Utf8Entry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Contains info from parsed bytecode
 */
public class ClassObject {

    private final ConstantPoolEntry[] constantPool;
    private final Info[] fieldInfos;
    private final Info[] methodInfos;

    private ClassObject(ConstantPoolEntry[] constantPool, Info[] fieldInfos, Info[] methodInfos) {
        this.constantPool = constantPool;
        this.fieldInfos = fieldInfos;
        this.methodInfos = methodInfos;
    }

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
                .map(this::createMethod)
                .collect(Collectors.toSet());
    }

    @NotNull
    private Method createMethod(Info mi) {
        final String descriptor = getUtf8(mi.getDescriptorIndex());
        final int split = descriptor.indexOf(')');
        final List<String> parameters = getParameterTypes(descriptor.substring(0, split));

        String returnType = descriptor.substring(split + 1);
        returnType = returnType.substring(0, returnType.length() - 1);
        return new Method(getUtf8(mi.getNameIndex()), parameters, returnType);
    }

    private List<String> getParameterTypes(String parameterDescriptor) {
        List<String> result = new ArrayList<>();
        boolean array = false;
        for (int i = 1; i < parameterDescriptor.length(); i++) {
            if (parameterDescriptor.charAt(i) == '[') {
                array = true;
            } else {
                if (parameterDescriptor.charAt(i) == 'L') {
                    int i2 = i;
                    while (i2 < parameterDescriptor.length() && parameterDescriptor.charAt(i2) != ';') {
                        i2++;
                    }
                    result.add(getArrayIndicator(array) + parameterDescriptor.substring(i, i2));
                    array = false;
                    i = i2;
                } else {
                    result.add(getArrayIndicator(array) + parameterDescriptor.charAt(i));
                    array = false;
                }
            }
        }
        return result;
    }

    @NotNull
    private String getArrayIndicator(boolean array) {
        return array ? "[" : "";
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

        private int constantPoolIndex = 0;
        private int fieldInfoIndex = 0;
        private int methodInfoIndex = 0;

        private ConstantPoolEntry[] constantPool;
        private Info[] fieldInfos;
        private Info[] methodInfos;

        public ClassObject build() {
            return new ClassObject(constantPool, fieldInfos, methodInfos);
        }

        public Builder<T> constantPoolCount(int constantPoolCount) {
            constantPool = new ConstantPoolEntry[constantPoolCount - 1];
            return this;
        }

        void constantPoolEntry(ConstantPoolEntry entry) {
            constantPool[constantPoolIndex++] = entry;
        }

        public Builder<T> fieldInfoCount(int fieldInfoCount) {
            fieldInfos = new Info[fieldInfoCount];
            return this;
        }

        public Builder<T> fieldInfo(Info fieldInfo) {
            fieldInfos[fieldInfoIndex++] = fieldInfo;
            return this;
        }

        public Builder<T> methodInfoCount(int methodInfoCount) {
            methodInfos = new Info[methodInfoCount];
            return this;
        }

        public Builder<T> methodInfo(Info methodInfo) {
            methodInfos[methodInfoIndex++] = methodInfo;
            return this;
        }
    }
}

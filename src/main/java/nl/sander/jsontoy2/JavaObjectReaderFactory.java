package nl.sander.jsontoy2;

import javassist.*;
import nl.sander.jsontoy2.javassist.ClassCreationException;
import nl.sander.jsontoy2.javassist.Javassist;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JavaObjectReaderFactory {
    public static final String ROOT_PACKAGE = "serializer.";
    private final static CtClass DESERIALIZER_BASE = Javassist.getTypeDefinition(JsonValueReader.class);
    private static final CtClass[] NO_EXCEPTIONS = {};
    private static final CtClass[] PARSER_PARAM = {Javassist.getTypeDefinition(Parser.class)};
    private static final Class<?>[] NO_PARAMS = {};
    private static final CtClass[] CT_NO_PARAMS = {};
    private final static CtClass OBJECT_CLASS = Javassist.getTypeDefinition(Object.class);
    private final static CtClass BOOLEAN = Javassist.getTypeDefinition(boolean.class);
    private final static CtClass INT = Javassist.getTypeDefinition(int.class);
    private final static CtClass LONG = Javassist.getTypeDefinition(long.class);
    private final static CtClass BYTE = Javassist.getTypeDefinition(byte.class);
    private final static CtClass SHORT = Javassist.getTypeDefinition(short.class);
    private final static CtClass FLOAT = Javassist.getTypeDefinition(float.class);
    private final static CtClass DOUBLE = Javassist.getTypeDefinition(double.class);

    @SuppressWarnings("unchecked")
    static <T> JsonValueReader<T> createReaderInstance(Class<T> type) {
        try {
            Class<?> jsonTypeReader = createReaderClass(type);
            return (JsonValueReader<T>) jsonTypeReader.getDeclaredConstructor(NO_PARAMS).newInstance(new Object[]{});

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new JsonParseException(e);
        }
    }

    private static Class<?> createReaderClass(Class<?> type) {
        String name = createDeserializerName(type);
        CtClass deserializerClass = Javassist.createClass(name, DESERIALIZER_BASE);

        try {
            deserializerClass.addConstructor(CtNewConstructor.make(CT_NO_PARAMS, NO_EXCEPTIONS, "{super();}", deserializerClass));
            deserializerClass.addMethod(createReadJsonMethod(deserializerClass, type));
            return deserializerClass.toClass();
        } catch (CannotCompileException e) {
            throw new ClassCreationException(e);
        }
    }

    private static CtMethod createReadJsonMethod(CtClass serializerClass, Class<?> type) {
        try {
            String readMethodBodySource = createReadMethodBodySource(type);
            System.out.println(readMethodBodySource);
            return CtNewMethod.make(Modifier.PUBLIC, OBJECT_CLASS, "read", PARSER_PARAM, NO_EXCEPTIONS, readMethodBodySource, serializerClass);
        } catch (CannotCompileException e) {
            throw new ClassCreationException(e);
        }
    }


    private static String createReadMethodBodySource(Class<?> type) {
        String source = "{";
        String typeName = type.getName();
        if (ReaderFactory.readerSuppliers.containsKey(type)) {
            source += "return " + JsonReader.class.getName() + ".read(" + typeName + ".class, $1);";
        } else {
            source += "java.util.Map object=" + JsonReader.class.getName() + ".readJavaObject(" + typeName + ".class,$1);\n";
            source += typeName + " instance = new " + typeName + "();\n";

            for (Field field : type.getDeclaredFields()) {
                source += "instance.set" + capitalize(field.getName())
                        + "(" + getSourceForGetValueFromObject(field) + ");\n";
            }

            source += "return instance;";
        }

        source += "}\n";
        return source;
    }

    @NotNull
    private static String getSourceForGetValueFromObject(Field field) {
        Class<?> fieldType = field.getType();
        String fieldName = field.getName();
        if (fieldType == boolean.class) {
            return "getBoolean(\"" + fieldName + "\", object)";
        } else if (fieldType == int.class) {
            return "getInt(\"" + fieldName + "\", object)";
        } else if (fieldType == long.class) {
            return "getLong(\"" + fieldName + "\",object)";
        } else if (fieldType == short.class) {
            return "getShort(\"" + fieldName + "\",object)";
        } else if (fieldType == byte.class) {
            return "getByte(\"" + fieldName + "\",object)";
        } else if (fieldType == float.class) {
            return "getFloat(\"" + fieldName + "\",object)";
        } else if (fieldType == double.class) {
            return "getDouble(\"" + fieldName + "\",object)";
        } else if (Set.class.isAssignableFrom(fieldType)) {
            return "getSet(\"" + fieldName + "\",object)";
        } else {
            return "(" + fieldType.getName() + ")(object.get(\"" + fieldName + "\"))";
        }
    }


    private static String genericType(CtField field) {
        try {
            if (!Javassist.isCollection(field.getType())) {
                return "";
            } else {

                String genericSignature = field.getGenericSignature(); // java.util.List<java.lang.String;>;
                Pattern p = Pattern.compile("(.+?)<(.+?);>;");
                Matcher matcher = p.matcher(genericSignature);
                if (matcher.find()) {
                    String[] genericTypes = matcher.group(2).substring(1).replaceAll("/", ".").split(";L");
                    for (int i = 0; i < genericTypes.length; i++) {
                        genericTypes[i] += ".class";
                    }
                    return String.join(",", genericTypes);
                }
                throw new ClassCreationException("Generic type not ok");
            }
        } catch (NotFoundException e) {
            throw new ClassCreationException(e);
        }
    }

    private static String capitalize(String lowercase) {
        return lowercase.substring(0, 1).toUpperCase() + lowercase.substring(1);
    }

    private static Set<String> createTypeList(Class<?>... classes) {
        return Arrays.stream(classes).map(Class::getName).collect(Collectors.toSet());
    }

    /*
     * custom root package is prepended to avoid the java.lang class in which it's illegal to create new classes
     *
     * Array marks ( '[]' ) are replaced by the 'Array', Otherwise the SerializerClassName would be syntactically incorrect
     */
    private static String createDeserializerName(Class<?> type) {
        return ROOT_PACKAGE + type.getName().replaceAll("\\[]", "Array") + "Deserializer";
    }
}

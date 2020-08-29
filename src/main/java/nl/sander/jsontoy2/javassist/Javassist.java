package nl.sander.jsontoy2.javassist;

import javassist.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * Facade for javassist functions
 */
public class Javassist {
    private static final String STRING = "java.lang.String";
    private static final String BOOLEAN = "java.lang.Boolean";
    private static final String CHARACTER = "java.lang.Character";
    private static final String BYTE = "java.lang.Byte";
    private static final String DOUBLE = "java.lang.Double";
    private static final String FLOAT = "java.lang.Float";
    private static final String LONG = "java.lang.Long";
    private static final String SHORT = "java.lang.Short";
    private static final String INTEGER = "java.lang.Integer";

    private final static Set<String> wrappersAndString = new HashSet<>(asList(BOOLEAN, CHARACTER, BYTE, DOUBLE, FLOAT, LONG, SHORT, INTEGER, STRING));
    private final static Set<String> wrappers = new HashSet<>(asList(BOOLEAN, CHARACTER, BYTE, DOUBLE, FLOAT, LONG, SHORT, INTEGER));

    private static final String COLLECTION = "java.util.Collection";
    private static final String LIST = "java.util.List";
    private static final String SET = "java.util.Set";
    private static final String MAP = "java.util.Map";

    private final static ClassPool pool = ClassPool.getDefault();

    public static CtClass getTypeDefinition(Class<?> type) {
        try {
            return pool.get(type.getName());
        } catch (NotFoundException e) {
            throw new ClassCreationException(e);
        }
    }

    public static CtClass getTypeDefinition(String type) {
        try {
            return pool.get(type);
        } catch (NotFoundException e) {
            throw new ClassCreationException(e);
        }
    }

    public static CtClass createClass(String className, CtClass baseClass) {
        return pool.makeClass(className, baseClass);
    }

    public static boolean isPrimitiveOrWrapperOrString(CtClass beanClass) {
        return beanClass.isPrimitive() || wrappersAndString.contains(beanClass.getName());
    }

    public static boolean isBasicType(CtClass beanClass) {
        return isPrimitiveOrWrapperOrString(beanClass) || isList(beanClass);
    }

    public static boolean isList(CtClass type) {
        try {
            List<CtClass> interfaces = new ArrayList<>(asList(type.getInterfaces()));
            interfaces.add(type);
            for (CtClass interfaze : interfaces) {
                if (interfaze.getName().equals(COLLECTION) || interfaze.getName().equals(LIST) || interfaze.getName().equals(SET)) {
                    return true;
                }
            }
            return false;
        } catch (NotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean isCollection(CtClass type) {
        try {
            List<CtClass> interfaces = new ArrayList<>(asList(type.getInterfaces()));
            interfaces.add(type);
            for (CtClass interfaze : interfaces) {
                if (interfaze.getName().equals(COLLECTION) || interfaze.getName().equals(LIST) || interfaze.getName().equals(SET) || interfaze.getName().equals(MAP)) {
                    return true;
                }
            }
            return false;
        } catch (NotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean isMap(CtClass type) throws NotFoundException {
        List<CtClass> interfaces = new ArrayList<>(asList(type.getInterfaces()));
        interfaces.add(type);
        for (CtClass interfaze : interfaces) {
            if (interfaze.getName().equals(MAP)) {
                return true;
            }
        }
        return false;
    }

    /*
     * Retrieves getter methods from a class
     */
    public static List<CtMethod> getGetters(CtClass type) {
        List<CtMethod> methods = new ArrayList<>();
        List<CtField> fields = getAllFields(type);

        for (CtField field : fields) {
            try {
                CtMethod method = type.getMethod(getGetterMethod(field), getDescription(field));
                if (Modifier.isPublic(method.getModifiers())) {
                    methods.add(method);
                }
            } catch (NotFoundException n) {
                // ignore
            }
        }
        return methods;
    }

    private static String getGetterMethod(CtField field) {
        return "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
    }

    private static List<CtField> getAllFields(CtClass beanClass) {
        try {
            List<CtField> allfields = new ArrayList<>(asList(beanClass.getDeclaredFields()));
            if (beanClass.getSuperclass() != null) {
                return getAllFields(beanClass.getSuperclass(), allfields);
            }
            return allfields;
        } catch (NotFoundException e) {
            throw new ClassCreationException(e);
        }
    }

    private static List<CtField> getAllFields(CtClass beanClass, List<CtField> allfields) {
        allfields.addAll(asList(beanClass.getDeclaredFields()));

        return allfields;
    }

    private static String getDescription(CtField field) throws NotFoundException {
        if (field.getType().isArray()) {
            return "()[" + innerClassName(field.getType().getName()) + ";";
        } else if (!field.getType().isPrimitive()) {
            return "()" + innerClassName(field.getType().getName()) + ";";
        } else {
            return "()" + asPrimitive(field.getType().getName());
        }
    }

    private static String innerClassName(String name) {
        return "L" + name.replaceAll("\\.", "/").replaceAll("\\[]", "");
    }

    private static String asPrimitive(String name) {
        switch (name) {
            case "int":
                return "I";
            case "byte":
                return "B";
            case "float":
                return "F";
            case "long":
                return "J";
            case "boolean":
                return "Z";
            case "char":
                return "C";
            case "double":
                return "D";
            case "short":
                return "S";
        }
        return "";
    }
}


package nl.sander.jsontoy2;

public class AccesModifiers {
    public final static int ACC_PUBLIC = 0x0001;        // Declared public; may be accessed from outside its package.
    public final static int ACC_FINAL = 0x0010;         // Declared final; no subclasses allowed.
    public final static int ACC_SUPER = 0x0020;         // Treat superclass methods specially when invoked by the invokespecial instruction.
    public final static int ACC_INTERFACE = 0x0200;     // Is an interface, not a class.
    public final static int ACC_ABSTRACT = 0x0400;      // Declared abstract; must not be instantiated.
    public final static int ACC_SYNTHETIC = 0x1000;     // Declared synthetic; not present in the source code.
    public final static int ACC_ANNOTATION = 0x2000;    // Declared as an annotation type.
    public final static int ACC_ENUM = 0x4000;          // Declared as an enum type.
}

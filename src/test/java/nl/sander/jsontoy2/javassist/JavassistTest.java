package nl.sander.jsontoy2.javassist;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import nl.sander.jsontoy2.testobjects.BooleanBean;
import nl.sander.jsontoy2.testobjects.SimpleBean;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JavassistTest {

    @Test
    public void getTypeDefinitionForClass() throws NotFoundException {
        // Act
        CtClass typeDefinition = Javassist.getTypeDefinition(BooleanBean.class);

        // Assert
        assertEquals("setValue", typeDefinition.getDeclaredMethod("setValue").getName());
    }

    @Test
    public void getTypeDefinitionForClassname() throws NotFoundException {
        // Act
        CtClass typeDefinition = Javassist.getTypeDefinition("nl.sander.jsontoy2.testobjects.BooleanBean");

        // Assert
        assertNotNull(typeDefinition);
        assertEquals("BooleanBean", typeDefinition.getSimpleName());
        assertEquals("nl.sander.jsontoy2.testobjects.BooleanBean", typeDefinition.getName());
        assertEquals("value", typeDefinition.getDeclaredField("value").getName());
        assertEquals("setValue", typeDefinition.getDeclaredMethod("setValue").getName());
    }

    @Test
    public void createClass() {
        // Act
        CtClass newClass = Javassist.createClass("UberClass", Javassist.getTypeDefinition(Object.class));

        // Assert
        assertEquals("UberClass", newClass.getName());
    }

    @Test
    public void isCollection() {
        // Act/ Assert
        assertTrue(Javassist.isCollection(Javassist.getTypeDefinition(List.class)));
        assertTrue(Javassist.isCollection(Javassist.getTypeDefinition(HashMap.class)));
        assertFalse(Javassist.isCollection(Javassist.getTypeDefinition(String.class)));
    }

    @Test
    public void getGetterMethod() {
        // Arrange
        CtClass typeDefinition = Javassist.getTypeDefinition(SimpleBean.class);

        // Act
        List<CtMethod> getterMethods = Javassist.getGetters(typeDefinition);

        // Assert
        assertEquals(2, getterMethods.size());
        assertEquals("getData1", getterMethods.get(0).getName());
        assertEquals("getData2", getterMethods.get(1).getName());
    }
}

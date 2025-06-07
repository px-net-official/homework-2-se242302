package at.pxnet;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    void classExists() {
        assertNotNull(getPersonClass());
    }

    @Test
    void classHasNoDefaultConstructor() {
        var hasDefaultConstructor = Arrays.stream(getPersonClass().getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertFalse(hasDefaultConstructor);
    }

    @Test
    void classHasCorrectConstructor() {
        assertNotNull(getPersonConstructor());
    }

    @Test
    void constructorThrowsExceptionForInvalidGrades() {
        assertDoesNotThrow(() -> callPersonConstructor("123123", "Max Müller", 20, new int[] {}));
        assertDoesNotThrow(() -> callPersonConstructor("123123", "Max Müller", 20, new int[] {1, 2, 3, 4, 5}));
        assertDoesNotThrow(() -> callPersonConstructor("123123", "Max Müller", 20, new int[] {5}));

        assertThrows(IllegalArgumentException.class, () -> callPersonConstructor("123123", "Max Müller", 20, new int[] {2, 2, 0, 2}));
        assertThrows(IllegalArgumentException.class, () -> callPersonConstructor("123123", "Max Müller", 20, new int[] {2, 6, 2, 2}));
    }

    @Test
    void classDoesNotHavePublicFields() {
        var fields = Arrays.stream(getPersonClass()
                        .getDeclaredFields())
                .filter(field -> Modifier.isPublic(field.getModifiers()))
                .toList();

        assertEquals(0, fields.size());
    }

    @Test
    void fieldIdIsReadonly() {
        var idField = Arrays.stream(getPersonClass()
                        .getDeclaredFields())
                .filter(field -> field.getName().equals("id"))
                .findFirst();

        assertTrue(idField.isPresent());
        assertTrue(Modifier.isFinal(idField.get().getModifiers()));
    }

    public static Object callPersonConstructor(String id, String name, int age, int[] grades) {
        try {
            return getPersonConstructor().newInstance(id, name, age, grades);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof RuntimeException runtimeException) {
                throw runtimeException;
            } else {
                throw new AssertionError(e);
            }
        }
    }

    private static Class<?> getPersonClass() {
        try {
            return PersonTest.class.getClassLoader().loadClass("at.pxnet.Person");
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }

    private static Constructor<?> getPersonConstructor() {
        var constructors = Arrays.stream(getPersonClass().getConstructors())
                .filter(constructor -> constructor.getParameterCount() == 4)
                .toList();

        assertEquals(1, constructors.size());

        return constructors.getFirst();
    }
}
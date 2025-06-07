package at.pxnet;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GradingTest {

    @Test
    void classExists() {
        assertNotNull(getGradingClass());
    }

    @Test
    void classHasNoPublicConstructor() {
        var hasAnyButPrivateConstructor = Arrays.stream(getGradingClass().getConstructors())
                .anyMatch(constructor -> constructor.getModifiers() != Modifier.PRIVATE);

        assertFalse(hasAnyButPrivateConstructor);
    }

    @Test
    void isFailingEvaluatesCorrectly() {
        assertFalse(isFailing(PersonTest.callPersonConstructor("123123", "Paula Pauke", 23, new int[]{1, 2, 3, 4})));
        assertFalse(isFailing(PersonTest.callPersonConstructor("123123", "Paula Pauke", 23, new int[]{1, 1, 1, 1})));
        assertFalse(isFailing(PersonTest.callPersonConstructor("123123", "Paula Pauke", 23, new int[]{4, 4})));

        assertTrue(isFailing(PersonTest.callPersonConstructor("123123", "Paula Pauke", 23, new int[]{1, 2, 3, 4, 5})));
        assertTrue(isFailing(PersonTest.callPersonConstructor("123123", "Paula Pauke", 23, new int[]{1, 2, 4, 4, 4})));

        assertThrows(IllegalStateException.class, () -> isFailing(PersonTest.callPersonConstructor("123123", "Paula Pauke", 23, new int[]{1})));
        assertThrows(IllegalStateException.class, () -> isFailing(PersonTest.callPersonConstructor("123123", "Paula Pauke", 23, new int[]{5})));
    }

    private static Class<?> getGradingClass() {
        try {
            return PersonTest.class.getClassLoader().loadClass("at.pxnet.Grading");
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }

    private boolean isFailing(Object person) {
        var method = Arrays.stream(getGradingClass().getMethods())
                .filter(methodDefinition -> Modifier.isPublic(methodDefinition.getModifiers()))
                .filter(methodDefinition -> Modifier.isStatic(methodDefinition.getModifiers()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Method not found"));

        try {
            return (boolean)method.invoke(null, person);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof RuntimeException runtimeException) {
                throw runtimeException;
            } else {
                throw new AssertionError(e);
            }
        }

    }
}

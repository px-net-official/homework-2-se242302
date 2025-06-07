package at.pxnet;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UniversityTest {

    @Test
    void classExists() {
        assertNotNull(getUniversityClass());
    }

    @Test
    void classHasDefaultConstructor() {
        var hasDefaultConstructor = Arrays.stream(getUniversityClass().getConstructors())
                .allMatch(constructor -> constructor.getParameterCount() == 0 && Modifier.isPublic(constructor.getModifiers()));

        assertTrue(hasDefaultConstructor);
    }

    @Test
    void getStudentsIsUnmodifiable() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var university = getUniversityConstructor().newInstance();
        var students = getStudents(university);
        assertThrows(UnsupportedOperationException.class, () -> students.add(PersonTest.callPersonConstructor("123123", "Charley Cook", 30, new int[] {1, 2, 3})));
    }

    @Test
    void getFailingStudentsReturnsEmptyList() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var university = getUniversityConstructor().newInstance();
        addStudent(university, new int[] {1, 2, 3});
        addStudent(university, new int[] {1, 2, 3});
        addStudent(university, new int[] {1, 2, 3});

        var result = getFailingStudents(university);
        assertTrue(result.isEmpty());
    }

    @Test
    void getFailingStudentsReturnsAllFailingStudents() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var university = getUniversityConstructor().newInstance();
        addStudent(university, new int[] {1, 2, 3});
        addStudent(university, new int[] {1, 2, 5});
        addStudent(university, new int[] {4, 4, 4});

        var result = getFailingStudents(university);
        assertEquals(2, result.size());
    }

    @Test
    void getAverageAgeReturnsZeroWithoutStudents() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var university = getUniversityConstructor().newInstance();

        var result = getAverageAge(university);
        assertEquals(0, result);
    }

    @Test
    void getAverageAgeReturnsAverage() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var university = getUniversityConstructor().newInstance();
        addStudent(university, 20);
        addStudent(university, 30);
        addStudent(university, 40);

        var result = getAverageAge(university);
        assertEquals(30, result);
    }

    private Class<?> getUniversityClass() {
        try {
            return PersonTest.class.getClassLoader().loadClass("at.pxnet.University");
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }

    private Constructor<?> getUniversityConstructor() {
        return Arrays.stream(getUniversityClass().getConstructors())
                .findAny()
                .orElseThrow();
    }

    private List<Object> getStudents(Object university) {
        try {
            var method = getUniversityClass().getMethod("getStudents");
            return (List<Object>) method.invoke(university);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError(e);
        }
    }

    private void addStudent(Object university, int[] grades) {
        var student = PersonTest.callPersonConstructor("123123", "Charley Cook", 30, grades);
        try {
            var method = Arrays.stream(getUniversityClass().getMethods())
                    .filter(methodDefinition -> methodDefinition.getName().equals("addStudent"))
                    .findAny()
                    .orElseThrow(() -> new AssertionError("Method not found"));

            method.invoke(university, student);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError(e);
        }
    }

    private void addStudent(Object university, int age) {
        var student = PersonTest.callPersonConstructor("123123", "Charley Cook", age, new int[] {1, 2, 3});
        try {
            var method = Arrays.stream(getUniversityClass().getMethods())
                    .filter(methodDefinition -> methodDefinition.getName().equals("addStudent"))
                    .findAny()
                    .orElseThrow(() -> new AssertionError("Method not found"));

            method.invoke(university, student);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError(e);
        }
    }

    private List<Object> getFailingStudents(Object university) {
        try {
            var method = getUniversityClass().getMethod("getFailingStudents");
            return (List<Object>) method.invoke(university);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError(e);
        }
    }

    private double getAverageAge(Object university) {
        try {
            var method = getUniversityClass().getMethod("getAverageAge");
            return (double) method.invoke(university);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError(e);
        }
    }
}

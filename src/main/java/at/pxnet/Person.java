package at.pxnet;

import java.util.Arrays;

import java.util.Arrays;

public class Person {
    private final String id;
    private String name;
    private int age;
    private int[] grades;

    public Person(String id, String name, int age, int[] grades) {
        if (grades == null || Arrays.stream(grades).anyMatch(g -> g < 1 || g > 5)) {
            throw new IllegalArgumentException("Grades must be between 1 and 5.");
        }

        this.id = id;
        this.name = name;
        this.age = age;
        this.grades = Arrays.copyOf(grades, grades.length);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int[] getGrades() {
        return Arrays.copyOf(grades, grades.length);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGrades(int[] grades) {
        if (grades == null || Arrays.stream(grades).anyMatch(g -> g < 1 || g > 5)) {
            throw new IllegalArgumentException("Grades must be between 1 and 5.");
        }
        this.grades = Arrays.copyOf(grades, grades.length);
    }

    public void setName(String name) {
        this.name = name;
    }
}



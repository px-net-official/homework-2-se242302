package at.pxnet;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        // Personen erstellen
        Person p1 = new Person("1", "Anna", 21, new int[]{1, 2, 3});
        Person p2 = new Person("2", "Ben", 22, new int[]{4, 4, 4});
        Person p3 = new Person("3", "Clara", 23, new int[]{3, 5});
        Person p4 = new Person("4", "David", 24, new int[]{1}); // Wird später IllegalStateException auslösen

        // Universität erstellen und Studenten hinzufügen
        University uni = new University();
        uni.addStudent(p1);
        uni.addStudent(p2);
        uni.addStudent(p3);
        uni.addStudent(p4);

        // Alle Studenten ausgeben
        System.out.println("\nAlle Studenten:");
        for (Person student : uni.getStudents()) {
            System.out.println(student.getName() + " (Alter: " + student.getAge() + ")");
        }

        // Durchschnittsalter berechnen
        System.out.println("\nDurchschnittsalter: " + uni.getAverageAge());

        // Durchgefallene Studenten anzeigen
        System.out.println("\nDurchgefallene Studenten:");
        List<Person> failing = uni.getFailingStudents();
        for (Person student : failing) {
            System.out.println(student.getName() + " ist durchgefallen.");
        }

        // Test: ist Person p2 durchgefallen?
        System.out.println("\nBen durchgefallen? " + Grading.isFailing(p2));

        // Ausnahme testen
        try {
            System.out.println("\nTest für ungültige Person:");
            Grading.isFailing(p4); // hat nur eine Note
        } catch (IllegalStateException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }
}

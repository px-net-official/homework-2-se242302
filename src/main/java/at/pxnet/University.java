package at.pxnet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

    public class University {
        private final List<Person> students = new ArrayList<>();

        public University() {
            // Standardkonstruktor
        }

        public void addStudent(Person person) {
            students.add(person);
        }

        public void removeStudent(Person person) {
            students.remove(person);
        }

        public List<Person> getStudents() {
            return Collections.unmodifiableList(students);
        }

        public List<Person> getFailingStudents() {
            List<Person> failing = new ArrayList<>();
            for (Person p : students) {
                try {
                    if (Grading.isFailing(p)) {
                        failing.add(p);
                    }
                } catch (IllegalStateException e) {
                    // Ungültige Personen überspringen
                }
            }
            return Collections.unmodifiableList(failing);
        }

        public double getAverageAge() {
            if (students.isEmpty()) {
                return 0;
            }

            double sum = 0;
            for (Person p : students) {
                sum += p.getAge();
            }

            return sum / students.size();
        }
    }


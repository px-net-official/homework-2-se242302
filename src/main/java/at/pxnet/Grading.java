package at.pxnet;

import java.util.Arrays;

public class Grading {
import java.util.Arrays;

    public final class grading {

        private grading() {
            // private constructor to prevent instantiation
        }

        public static boolean isFailing(Person person) {
            int[] grades = person.getGrades();

            if (grades.length < 2) {
                throw new IllegalStateException("Person must have at least two grades.");
            }

            long countFours = Arrays.stream(grades).filter(g -> g == 4).count();
            boolean hasFive = Arrays.stream(grades).anyMatch(g -> g == 5);

            return hasFive || countFours >= 3;
        }
    }

}

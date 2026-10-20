
import java.util.ArrayList;
import java.util.Scanner;

public class StudentMarksRecord {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> marksList = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("Number of students must be positive.");
            sc.close();
            return;
        }

        for (int i = 0; i < n; i++) {
            System.out.print("Enter marks for student " + (i + 1) + ": ");
            int marks = sc.nextInt();

            if (marks < 0 || marks > 100) {
                System.out.println("Invalid marks! Enter a value from 0 to 100.");
                i--;
                continue;
            }

            marksList.add(marks);
        }

        int highest = marksList.get(0);
        int lowest = marksList.get(0);
        int total = 0;

        for (int marks : marksList) {
            if (marks > highest) {
                highest = marks;
            }

            if (marks < lowest) {
                lowest = marks;
            }

            total += marks;
        }

        System.out.println("\n----- Student Marks Report -----");
        System.out.println("All marks: " + marksList);
        System.out.println("Highest marks: " + highest);
        System.out.println("Lowest marks: " + lowest);
        System.out.printf("Average marks: %.2f%n", (double) total / n);

        sc.close();
    }
}

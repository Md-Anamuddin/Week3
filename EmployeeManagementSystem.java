
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeManagementSystem {
    private static final String FILE_NAME = "employees.txt";

    private static final ArrayList<Employee> employees =
            new ArrayList<>();

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadEmployees();

        while (true) {
            System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Save Employees");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;

                case 2:
                    displayEmployees();
                    break;

                case 3:
                    searchEmployee();
                    break;

                case 4:
                    saveEmployees();
                    break;

                case 5:
                    saveEmployees();
                    System.out.println("Exiting Employee Management System.");
                    sc.close();
                    return;

                default:
                    System.out.println(
                        "Invalid choice. Please select from 1 to 5."
                    );
            }
        }
    }

    private static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input. Enter a whole number: ");
            sc.next();
        }
        return sc.nextInt();
    }

    private static double readSalary() {
        while (true) {
            if (sc.hasNextDouble()) {
                double salary = sc.nextDouble();

                if (Double.isFinite(salary) && salary >= 0) {
                    return salary;
                }
            } else {
                sc.next();
            }

            System.out.print(
                "Invalid salary. Enter a non-negative number: "
            );
        }
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();

            if (!value.isEmpty() && !value.contains("|")) {
                return value;
            }

            System.out.println(
                "Input cannot be empty or contain the | character."
            );
        }
    }

    private static void addEmployee() {
        System.out.print("Enter employee ID: ");
        int id = readInt();
        sc.nextLine();

        if (id <= 0) {
            System.out.println("Employee ID must be positive.");
            return;
        }

        for (Employee e : employees) {
            if (e.getId() == id) {
                System.out.println("Employee ID already exists.");
                return;
            }
        }

        String name = readText("Enter employee name: ");
        String department = readText("Enter department: ");

        System.out.print("Enter salary: Rs. ");
        double salary = readSalary();
        sc.nextLine();

        employees.add(new Employee(id, name, department, salary));

        System.out.println("Employee added successfully!");
        saveEmployees();
    }

    private static void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employee records found.");
            return;
        }

        System.out.println("\n----- Employee Records -----");

        for (Employee e : employees) {
            e.displayEmployee();
        }
    }

    private static void searchEmployee() {
        System.out.print("Enter employee ID to search: ");
        int id = readInt();

        for (Employee e : employees) {
            if (e.getId() == id) {
                System.out.println("Employee found:");
                e.displayEmployee();
                return;
            }
        }

        System.out.println("Employee not found.");
    }

    private static void saveEmployees() {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(FILE_NAME))) {

            for (Employee e : employees) {
                bw.write(e.toFileString());
                bw.newLine();
            }

            System.out.println("Employee data saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void loadEmployees() {
        try (BufferedReader br = new BufferedReader(
                new FileReader(FILE_NAME))) {

            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;

                try {
                    String[] data = line.split("\\|", -1);

                    if (data.length != 4) {
                        throw new IllegalArgumentException(
                            "Incorrect number of fields"
                        );
                    }

                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    String department = data[2];
                    double salary = Double.parseDouble(data[3]);

                    if (id <= 0 || name.trim().isEmpty()
                            || department.trim().isEmpty()
                            || !Double.isFinite(salary)
                            || salary < 0) {
                        throw new IllegalArgumentException(
                            "Invalid employee data"
                        );
                    }

                    boolean duplicate = false;

                    for (Employee e : employees) {
                        if (e.getId() == id) {
                            duplicate = true;
                            break;
                        }
                    }

                    if (duplicate) {
                        throw new IllegalArgumentException(
                            "Duplicate employee ID"
                        );
                    }

                    employees.add(
                        new Employee(id, name, department, salary)
                    );

                } catch (IllegalArgumentException e) {
                    System.out.println(
                        "Skipping invalid record at line "
                        + lineNumber + ": " + e.getMessage()
                    );
                }
            }

            System.out.println(
                employees.size() + " employee record(s) loaded."
            );

        } catch (IOException e) {
            System.out.println(
                "No saved employee data found yet. Starting fresh."
            );
        }
    }
}

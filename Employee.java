
public class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name,
                    String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public void displayEmployee() {
        System.out.printf(
            "ID: %d | Name: %s | Department: %s | Salary: Rs. %.2f%n",
            id, name, department, salary
        );
    }

    // Format used for saving a record to a text file.
    public String toFileString() {
        return id + "|" + name + "|" + department + "|" + salary;
    }
}

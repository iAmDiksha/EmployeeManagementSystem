package empMangSystem;

import java.io.*;
import java.util.*;

class Employee implements Serializable {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Dept: " + department + ", Salary: " + salary;
    }
}

class EmployeeService {
    private List<Employee> employees;
    private final String FILE_NAME = "employees.dat";

    public EmployeeService() {
        employees = new ArrayList<>();
        loadEmployees();
    }

    public void addEmployee(Employee employee) {
        for (Employee emp : employees) {
            if (emp.getId() == employee.getId()) {
                System.out.println("Employee ID already exists! Cannot add duplicate.");
                return;
            }
        }
        employees.add(employee);
        saveEmployees();
        System.out.println("Employee added successfully!");
    }

    public boolean updateEmployee(int id, String name, String dept, double salary) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                emp.setName(name);
                emp.setDepartment(dept);
                emp.setSalary(salary);
                saveEmployees();
                return true;
            }
        }
        System.out.println("Employee ID " + id + " not found!!");
        return false;
    }

    public boolean deleteEmployee(int id) {
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee emp = iterator.next();
            if (emp.getId() == id) {
                iterator.remove();
                saveEmployees();
                System.out.println("Employee deleted successfully!");
                return true;
            }
        }
        System.out.println("Employee ID " + id + " does not exist!");
        return false;
    }

    public void listEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
    }

    private void saveEmployees() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEmployees() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            employees = (List<Employee>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            employees = new ArrayList<>();
        }
    }
}

public class EmployeeManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeService service = new EmployeeService();

        while (true) {
            System.out.println("\n1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. List Employees");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = scanner.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = scanner.nextDouble();
                    service.addEmployee(new Employee(id, name, dept, salary));
                    break;
                case 2:
                    System.out.print("Enter Employee ID to Update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    if (!service.updateEmployee(updateId, "", "", 0)) break;
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter New Department: ");
                    String newDept = scanner.nextLine();
                    System.out.print("Enter New Salary: ");
                    double newSalary = scanner.nextDouble();
                    service.updateEmployee(updateId, newName, newDept, newSalary);
                    break;
                case 3:
                    System.out.print("Enter Employee ID to Delete: ");
                    int deleteId = scanner.nextInt();
                    service.deleteEmployee(deleteId);
                    break;
                case 4:
                    service.listEmployees();
                    break;
                case 5:
                    System.out.println("Exiting... Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again!");
            }
        }
    }
}


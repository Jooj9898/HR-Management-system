package ca.hrm;

import java.util.List;

public abstract class Employee implements Area{
    protected int id;
    protected String firstName;
    protected String surname;
    protected int managerId;
    protected int salary;  
    
    Employee(int id, String firstName, String surname, int managerId, int salary) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0.");
        }
        this.id = id;

        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        this.firstName = firstName;

        if (surname == null || surname.isEmpty()) {
            throw new IllegalArgumentException("Surname cannot be null or empty.");
        }
        this.surname = surname;

        if (managerId < 0) {
            throw new IllegalArgumentException("Manager id cannot be negative");
        }

        if (managerId == id) {
            throw new IllegalArgumentException("Manager id cannot be the same as employee id");
        }
        this.managerId = managerId;

        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.salary = salary;
    }

    public List<Employee> getManaged() { return null; }

    public int getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }   
    public String getSurname() {
        return surname;
    }
    public int getManagerId() {
        return managerId;
    }
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.salary = salary;
    }

    public String toString(String del) {
        return String.format("%d%s%s%s%s%s%s%s%b%s%d%s%d", 
            id, del, firstName, del, surname, del, getArea(), del, getManaged() != null, del, managerId, del, salary);
    }

    public String getEmailAddress() {
        return firstName.toLowerCase() + "." + surname.toLowerCase() + "@" + getArea() + ".company.com";
    }
}

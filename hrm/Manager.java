package ca.hrm;

import java.util.List;

public abstract class Manager extends Employee {    
    private List<Employee> managedEmployees;
    
    Manager(int id, String firstName, String surname, int managerId, int salary) {
        super(id, firstName, surname, managerId, salary);
        managedEmployees = new java.util.ArrayList<>();
    }
    
    @Override
    public List<Employee> getManaged() {
        return managedEmployees;
    }

    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        if (managedEmployees.contains(employee)) {
            throw new IllegalArgumentException("Employee is already managed.");
        }
        if (employee.getManagerId() != this.id) {
            throw new IllegalArgumentException("Employee is not managed by this manager.");
        }
        if (!employee.getArea().equals(this.getArea())) {
            throw new IllegalArgumentException("Manager and employee must be in the same area.");
        }
        managedEmployees.add(employee);
    }
}

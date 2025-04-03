package ca.hrm;

class AdminEmployee extends Employee {
    
    public AdminEmployee(int id, String firstName, String surname, int managerId, int salary) {
        super(id, firstName, surname, managerId, salary);
    }   

    @Override
    public String getArea() { return "admin"; } 
}
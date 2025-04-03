package ca.hrm;

class BusinessEmployee extends Employee {
    
    public BusinessEmployee (int id, String firstName, String surname, int managerId, int salary) {
        super(id, firstName, surname, managerId, salary);
    }   

    @Override
    public String getArea() { return "business"; } 
}
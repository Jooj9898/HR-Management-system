package ca.hrm;

class TechnicalEmployee extends Employee {
    
    public TechnicalEmployee(int id, String firstName, String surname, int managerId, int salary) {
        super(id, firstName, surname, managerId, salary);
    }   

    @Override
    public String getArea() { return "technical"; } 
}
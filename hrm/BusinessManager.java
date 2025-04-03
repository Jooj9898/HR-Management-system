package ca.hrm;

class BusinessManager extends Manager {
    
    public BusinessManager(int id, String firstName, String surname, int managerId, int salary) {
        super(id, firstName, surname, managerId, salary);
    }   

    @Override
    public String getArea() { return "business"; } 
}
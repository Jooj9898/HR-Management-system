package ca.hrm;

class AdminManager extends Manager {
    
    public AdminManager(int id, String firstName, String surname, int managerId, int salary) {
        super(id, firstName, surname, managerId, salary);
    }   

    @Override
    public String getArea() { return "admin"; } 
}
package ca.hrm;

class TechnicalManager extends Manager {
    
    public TechnicalManager(int id, String firstName, String surname, int managerId, int salary) {
        super(id, firstName, surname, managerId, salary);
    }   

    @Override
    public String getArea() { return "technical"; } 
}
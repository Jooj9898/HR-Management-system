package ca;

import ca.hrm.EmployeeList;
import ca.apis.EmailSystem;
import ca.apis.Notifications;

public class Main {

    public static void main(String[] args) {
        EmployeeList empList = new EmployeeList();
        empList.addEmployees("employees_v1.csv", true);
        empList.addAction("ListMngrsLT5", "List managers that manage fewer than 5 employees", 
            emp -> emp.getManaged() != null && emp.getManaged().size() < 5,
            emp -> System.out.printf("%d, %s, %s, %d, %d\n", emp.getId(), emp.getFirstName(), emp.getSurname(),
                                     emp.getManagerId() != 0 ? 1 : 2, emp.getManaged().size()));
        empList.addAction("Notifications", "Send notifications", 
            emp -> emp.getSurname().startsWith("A") && emp.getManaged() == null, 
            emp -> EmailSystem.send(emp.getEmailAddress(), "Dear " + emp.getFirstName() + ",\n\n" + 
                                    Notifications.getCurrent(emp.getArea()) + "\n\nRegards,\nThe communications team"));
        empList.addAction("SalaryUp10pc", "Increase salary by 10% for employees with salary < 40000", 
            emp -> emp.getSalary() < 40000, 
            emp -> emp.setSalary(Math.round((float)(emp.getSalary() * 1.1))));
            
        empList.run("ListMngrsLT5");
        empList.run("Notifications");
        empList.run("SalaryUp10pc");
        empList.exportToFile("employees_v2.csv");	
    }
}

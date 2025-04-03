package ca.hrm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.lang.RuntimeException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.HashMap;
import java.util.Map;

public class EmployeeList {
    private record Action(String description, Predicate<Employee> predicate, Consumer<Employee> consumer) {}

    List<Employee> employees = null;
    List<Manager> managers = new ArrayList<>();
    Map<String, Action> actions = new HashMap<>();

    public void addEmployee(String id, String first_name, String surname, String area, String is_manager, String manager_id, String salary) {
        if (employees == null) {
            employees = new ArrayList<>();
        }
        
        int empId = 0;
        try {
            empId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Employee id must be a positive integer @id=" + id);

        }
        boolean isMgr = false;
        try {
            isMgr = Boolean.parseBoolean(is_manager);
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Employee isManager must be true or false @id=" + id);
        }
        int mgrId = 0;
        try {
            mgrId = Integer.parseInt(manager_id);
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Employee id must be a non-negative integer @id=" + id);

        }
        int sal = 0;
        try {
            sal = Integer.parseInt(salary);
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Employee id must be a positive integer @id=" + id);
        }
    
        Employee employee = null;
        try {
            if (!isMgr) {
                switch (area) {
                    case "admin": employee = new AdminEmployee(empId, first_name, surname, mgrId, sal); break;
                    case "business": employee = new BusinessEmployee(empId, first_name, surname, mgrId, sal); break;
                    case "technical": employee = new TechnicalEmployee(empId, first_name, surname, mgrId, sal); break;
                    default: 
                        System.err.println("ERROR: Work area must be admin, business, or technical @id=" + id);
                        employee = null;        
                }
            } else {
                Manager manager = null;
                switch (area) {
                    case "admin": manager = new AdminManager(empId, first_name, surname, mgrId, sal); break;
                    case "business": manager = new BusinessManager(empId, first_name, surname, mgrId, sal); break;
                    case "technical": manager = new TechnicalManager(empId, first_name, surname, mgrId, sal); break;
                    default: 
                        System.err.println("ERROR: Work area must be admin, business, or technical @id=" + id);
                        manager = null;        
                }
                if (manager != null) {
                    managers.add(manager);
                    employee = manager;
                }
            }
        } catch (IllegalArgumentException e) {  
            System.err.println("ERROR: " + e.getMessage() + " @id=" + id);
        }
        if (employee == null) {
            System.err.println("ERROR: Employee not created @id=" + id);
            return;
        }   
        employees.add(employee);
    }

    public void addEmployees(String file_name, boolean heading) {
        Path filePath = Path.of(file_name); 
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.skip(heading ? 1: 0).forEach(line -> {
                String[] parts = line.split("\\s*,\\s*");
                if (parts.length == 7) {
                    addEmployee(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
                } else {
                    System.err.println("ERROR: Invalid line format: " + line);
                }
            });
        } catch (IOException e) {
            System.err.println("ERROR: Unable to read file " + file_name + ": " + e.getMessage());
        }
        try {
            if (employees != null) {
                employees.forEach(emp -> {
                    if (emp.managerId != 0) {
                        List<Manager> empMgrs = managers.stream().filter(mgr -> mgr.id == emp.managerId).toList();
                        if (empMgrs.isEmpty()) {  
                            System.err.println("ERROR: Non-existent manager id @id=" + emp.id);
                        } else if (empMgrs.size() > 1) {
                            throw new RuntimeException("More than one manager with id " + emp.managerId);
                        } else {
                            empMgrs.get(0).addEmployee(emp);
                        }
                    }
                });
            }
        } catch (IllegalArgumentException e) {
            System.err.println("ERROR: " + e.getMessage() + " @file=" + file_name);
        }
    }

    private void process(Predicate<Employee> predicate, Consumer<Employee> consumer) {
        if (employees == null) {
            System.err.println("ERROR: No employees to process.");
            return;
        }
        employees.stream().filter(predicate).forEach(emp -> consumer.accept(emp));
    }

    public void addAction(String name, String description, Predicate<Employee> predicate, Consumer<Employee> consumer) {
        actions.put(name, new Action(description, predicate, consumer));
    }

    public void run(String name) {
        if (actions.containsKey(name)) {
            Action action = actions.get(name);
            System.out.println("\nRunning action " + name + ": " + action.description);
            System.out.println("--------------------------------------------------");
            process(action.predicate, action.consumer);
            System.out.println("\n\n");
        } else {
            throw new IllegalArgumentException("No action found with name " + name);
        }
    }   

    public void exportToFile(String name) {
        if (employees == null) {
            System.err.println("ERROR: No employees to export.");
            return;
        }
        
        Path outputFile = Path.of(name);

        try (BufferedWriter writer = Files.newBufferedWriter(outputFile, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.WRITE)) {
            writer.write("id,first_name,surname,area,is_manager,manager_id,salary\n");
            employees.stream().map(emp -> emp.toString(",")).forEach(r -> { 
                try { 
                    writer.write(r + "\n");
                } catch (IOException e) {
                    System.err.println("ERROR: Unable to write to file: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.err.println("ERROR: Unable to write to file: " + e.getMessage());
        }
    }
}

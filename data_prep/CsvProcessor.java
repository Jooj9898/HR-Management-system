import java.io.*;
import java.nio.file.*;
import java.util.*;


public class CsvProcessor {
    public static void main(String[] args) throws IOException {
        String inputFile = "employees.csv";
        String outputFile = "../employees_v1.csv";
        
        List<String[]> rows = Files.lines(Paths.get(inputFile))
                .skip(1) // Skip header
                .map(line -> line.split(","))
                .toList();
        
        // initialise
        Random random = new Random();
        List<String[]> employees = new ArrayList<>();
        
        // assign areas and isManager indicators
        rows.forEach(row -> {
            row = Arrays.copyOf(row, row.length + 4);
            row[3] = assignArea(random);
            row[4] = (random.nextDouble() < 0.15) ? "true" : "false";
            employees.add(row);
        });
        
        // check for duplicate ids
        if (employees.size() != employees.stream().map(emp -> emp[0]).distinct().count()) {
            System.err.println("ERROR: There are duplicate manager IDs in the input file.");
            System.exit(1);
        }

       // identify managers
        List<String[]> managers = employees.stream().filter(r -> r[4] == "true").toList();        
        ArrayList<String[]> potentialFirstLineManagers = new ArrayList<>(managers);
        ArrayList<String[]> potentialSecondLineManagers = new ArrayList<>(managers);

        employees.forEach(row -> { 
            if (potentialSecondLineManagers.contains(row) && !potentialFirstLineManagers.contains(row)) {
                row[5] = "0";
            } else if (row[4] == "true") {
                String[] mngr = getRandomManager(potentialSecondLineManagers.stream().filter(r -> r != row && r[3] == row[3]).toList(), random);
                if (potentialFirstLineManagers.contains(mngr)) {
                    potentialFirstLineManagers.remove(mngr);
                } 
                row[5] = mngr[0];
            } else {
                String[] mngr = getRandomManager(potentialFirstLineManagers.stream().filter(r -> r != row && r[3] == row[3]).toList(), random);
                if (potentialSecondLineManagers.contains(mngr)) {
                    potentialSecondLineManagers.remove(mngr);
                } 
                row[5] = mngr[0];
            }
        });
        
        // assign salary
        employees.forEach(row -> row[6] = String.valueOf(generateSalary(row[4].equals("true"), random)));
        
        // Write output
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile))) {
            writer.write("id,first_name,surname,area,isManager,managerId,salary\n");
            for (String[] row : employees) {
                writer.write(String.join(",", row) + "\n");
            }
        }
    }
    
    private static String assignArea(Random random) {
        double p = random.nextDouble();
        return (p < 0.6) ? "business" : (p < 0.8) ? "technical" : "admin";
    }
    
    private static String[] getRandomManager(List<String[]> managers, Random random) {
        return managers.get(random.nextInt(managers.size()));
    }
    
    private static int generateSalary(boolean isManager, Random random) {
        double mean = isManager ? 70 : 50;
        double sd = isManager ? 15 : 10;
        return (int) Math.max(30, mean + sd * random.nextGaussian());
    }
}

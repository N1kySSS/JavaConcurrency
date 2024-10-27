package sum_in_files.util;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Saver {
    private static final String SAVING_DIRECTORY = "D:\\Data\\Projects\\JavaConcurrency\\src\\sum_in_files\\results";

    public static void save(String filename, Object result, double executionTime) {
        try {
            Files.createDirectories(Paths.get(SAVING_DIRECTORY));
        } catch (IOException e) {
            System.out.println("Failed to create directory: " + e.getMessage());
            return;
        }

        String filePath = Paths.get(SAVING_DIRECTORY, filename).toString();

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Result: " + result.toString() + "\n");
            writer.write("Execution Time: " + executionTime + " milliseconds\n");
            System.out.println("Result saved to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving result: " + e.getMessage());
        }
    }
}

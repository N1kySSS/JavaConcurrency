package sum_in_files.util;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Saver {
    private static final String SAVING_DIRECTORY = "src/sum_in_files/results";

    public static void save(String filename, Object result, double executionTime) {
        Path directoryPath = Paths.get(SAVING_DIRECTORY);

        try {
            Files.createDirectories(directoryPath);
        } catch (IOException ioException) {
            System.out.println("Ошибка создания пакета: " + ioException.getMessage());
            return;
        }

        String filePath = directoryPath.resolve(filename).toString();

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Result: " + result.toString() + "\n");
            writer.write("Execution Time: " + executionTime + " milliseconds\n");
            System.out.println("Result saved to " + filePath);
        } catch (IOException ioException) {
            System.out.println("Ошибка сохранения результата: " + ioException.getMessage());
        }
    }
}

package parallel_minor.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FindingDeterminantResultSaver {

    private static final String resultDirectory = "src/parallel_minor/results";

    public static void save(String resultFileName, long executionTimeMillis, BigInteger result) {
        if (!resultFileName.endsWith(".txt")) {
            System.out.println("Неверное расширение файла");
            return;
        }

        Path directory = Paths.get(resultDirectory);

        try {
            Files.createDirectories(directory);
        } catch (IOException ioException) {
            System.err.println("Ошибка создания пакета: " + ioException.getMessage());
            return;
        }

        Path filePath = directory.resolve(resultFileName);
        File fileWithResult = createFileIfNotExists(filePath);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileWithResult))) {
            writer.write("Determinant: " + result);
            writer.newLine();
            writer.write("Execution Time: " + executionTimeMillis + " milliseconds");
            System.out.println("Результаты успешно сохранены в " + fileWithResult.getName());
        } catch (IOException ioException) {
            System.err.println("Ошибка сохранения результата: " + ioException.getMessage());
        }
    }

    private static File createFileIfNotExists(Path filePath) {
        File file = new File(filePath.toString());

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Файл " + file.getName() + " создан");
                }
            } catch (IOException ioException) {
                System.err.println("Ошибка создания файла: " + ioException.getMessage());
            }
        }

        return file;
    }
}

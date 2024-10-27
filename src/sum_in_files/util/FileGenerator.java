package sum_in_files.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class FileGenerator {

    private final Random random = new Random();

    public void createFiles(String directoryPath, int n) {
        Path directory = Paths.get(directoryPath);

        try {
            Files.createDirectories(directory);
        } catch (IOException ioException) {
            System.out.println("Ошибка создания пакета: " + ioException.getMessage());
            return;
        }

        for (int i = 1; i <= n; i++) {
            Path filePath = directory.resolve(i + ".txt");
            File file = new File(filePath.toString());

            try {
                if (file.createNewFile()) {
                    System.out.println("Файл " + file.getName() + " создан");
                } else {
                    System.out.println("Файл " + file.getName() + " уже существует");
                }
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }
    }

    public void createFile(String path) {
        Path filePath = Paths.get(path);

        try {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        } catch (IOException ioException) {
            System.out.println("Ошибка создания файла: " + ioException.getMessage());
        }
    }


    public void fillFilesWithNumbers(String directoryPath, int countFiles, int linesInFile) {
        Path directory = Paths.get(directoryPath);

        try {
            Files.createDirectories(directory);
        } catch (IOException ioException) {
            System.out.println("Ошибка создания пакета: " + ioException.getMessage());
            return;
        }

        for (int i = 1; i < countFiles; i++) {
            Path filePath = directory.resolve(i + ".txt");
            File file = filePath.toFile();

            if (!file.exists()) {
                createFile(filePath.toString());
            }

            if (file.length() > 0) {
                System.out.println("Файл " + file.getName() + " уже заполнен");
                continue;
            }

            try (FileWriter writer = new FileWriter(file)) {
                for (int j = 0; j < linesInFile; j++) {
                    int randomNumber = generateValue();
                    writer.write(randomNumber + System.lineSeparator());
                }

            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }
    }

    public int generateValue() {
        return random.nextInt(-10, 11);
    }

    public void clearFileContents(String directoryPath) {
        Path directory = Paths.get(directoryPath);

        if (!Files.exists(directory) || !Files.isDirectory(directory)) {
            return;
        }

        try (var filePaths = Files.list(directory)) {
            filePaths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try (FileWriter writer = new FileWriter(filePath.toFile())) {
                        writer.write("");
                        System.out.println("Содержимое файла " + filePath.getFileName() + " очищено.");
                    } catch (IOException ioException) {
                        System.out.println("Ошибка при очистке файла " +
                                filePath.getFileName() + ": " + ioException.getMessage());
                    }
                }
            });
        } catch (IOException ioException) {
            System.out.println("Ошибка при очистке файлов в директории: " + ioException.getMessage());
        }
    }
}

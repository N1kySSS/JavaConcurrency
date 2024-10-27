package sum_in_files.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FileGenerator {

    private final Random random = new Random();

    public void createFiles(String directoryPath, int n) {
        for (int i = 1; i <= n; i++) {
            File file = new File(directoryPath + "/" + i + ".txt");

            try {
                if (file.createNewFile()) {
                    System.out.println("Файл " + file.getName() + " создан");
                } else {
                    System.out.println("Файл " + file.getName() + " уже существует");
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public void createFile(String path) {
        File file = new File(path);
        file.getParentFile().mkdirs();

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void fillFilesWithNumbers(String directoryPath, int countFiles, int linesInFile) {
        for (int i = 1; i < countFiles; i++) {
            String path = directoryPath + "/" + i + ".txt";
            File file = new File(path);

            if (!file.exists()) {
                createFile(path);
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

            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public int generateValue() {
        return random.nextInt(-10, 11);
    }

    public void clearFileContents(String directoryPath) {
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {

                if (file.isFile()) {
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write("");  // Записываем пустую строку
                        System.out.println("Содержимое файла " + file.getName() + " очищено.");
                    } catch (IOException e) {
                        System.out.println("Ошибка при очистке файла " + file.getName() + ": " + e.getMessage());
                    }
                }
            }
        }
    }
}

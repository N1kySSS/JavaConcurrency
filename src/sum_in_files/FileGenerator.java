package sum_in_files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FileGenerator {

    private static final int COUNT_FILES = 13;
    private static final File DIRECTORY = new File("D:\\Data\\Projects\\JavaConcurrency\\src\\sum_in_files\\files");

    private final Random random = new Random();

    public void createFiles() {
        for (int i = 1; i <= COUNT_FILES; i++) {
            File file = new File(DIRECTORY + "/" + i + ".txt");

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


    public void fillFilesWithNumbers(String directoryPath, int linesInFile) {
        for (int i = 1; i < COUNT_FILES; i++) {
            String path = directoryPath + "/" + i + ".txt";
            File file = new File(path);

            if (!file.exists()) {
                createFile(path);
            }

            try(FileWriter writer = new FileWriter(file)) {
                for (int j = 0; j < linesInFile; j++) {
                    int randomNumber = generateValue();
                    writer.write(randomNumber);
                }

            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public int generateValue() {
        return random.nextInt(-10, 11);
    }
}

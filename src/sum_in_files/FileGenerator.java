package sum_in_files;

import java.io.File;
import java.io.IOException;

public class FileGenerator {

    private static final int countFiles = 13;

    private void createFiles() {
        File directory = new File("D:\\Data\\Projects\\JavaConcurrency\\src\\sum_in_files\\files");

        for(int i = 0; i < countFiles; i++) {
            File file = new File(directory + "/" + i + ".txt");
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
}

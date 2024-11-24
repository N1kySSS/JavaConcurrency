package parallel_minor.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class MatrixGenerator {

    private final int order;
    private final Random random = new Random();

    private final String directoryPath = "src/parallel_minor/matrix";
    private String fileName;

    public MatrixGenerator(int order) {
        this.order = order;
    }

    private File createMatrixFile() {
        Path directory = Paths.get(directoryPath);

        try {
            Files.createDirectories(directory);
        } catch (IOException ioException) {
            System.err.println("Ошибка создания пакета: " + ioException.getMessage());
            return null;
        }

        Path pathFile = directory.resolve(order + ".txt");
        File file = new File(pathFile.toString());

        try {
            if (file.createNewFile()) {
                System.out.println("Файл " + file.getName() + "успешно создан");
            }
        } catch (IOException ioException) {
            System.err.println("Ошибка создания файла: " + ioException.getMessage());
            return null;
        }

        return file;
    }

    private int[][] generateMatrix() {
        int[][] matrix = new int[order][order];

        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                matrix[i][j] = random.nextInt(-100, 100);
            }
        }

        return matrix;
    }

    private void saveMatrixToFile(int[][] matrix, String filePath) {
        File file = new File(filePath);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int[] row : matrix) {
                for (int number : row) {
                    writer.write(number + " ");
                }

                writer.newLine();
            }
        } catch (IOException ioException) {
            System.err.println("Ошибка записи в файл: " + ioException.getMessage());
        }
    }

    public void createAndSaveMatrix() {
        File file = createMatrixFile();
        if (file != null) {
            fileName = file.getName();
            int[][] matrix = generateMatrix();
            saveMatrixToFile(matrix, file.getPath());
        }
    }

    public String getFilePath() {
        return directoryPath + "/" + fileName;
    }
}

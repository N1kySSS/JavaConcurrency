package parallel_minor.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatrixReader {

    public static int[][] readMatrixFromFile(String filePath) {
        List<int[]> rows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] numbers = line.trim().split("\\s+");
                int[] row = new int[numbers.length];

                for (int i = 0; i < numbers.length; i++) {
                    row[i] = Integer.parseInt(numbers[i]);
                }

                rows.add(row);
            }
        } catch (IOException ioException) {
            System.err.println("Ошибка чтения файла: " + ioException.getMessage());
        }

        return rows.toArray(new int[0][0]);
    }
}

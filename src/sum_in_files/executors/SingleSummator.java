package sum_in_files.executors;

import java.io.*;
import java.util.Scanner;

public class SingleSummator {

    protected int sum = 0;

    public int sumAllFiles(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files == null) {
            return Integer.MIN_VALUE;
        }

        for (File file : files) {
            try(Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    try {
                        int number = Integer.parseInt(scanner.nextLine());
                        sum += number;
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println(numberFormatException.getMessage());
                    }
                }
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println(fileNotFoundException.getMessage());;
            }
        }

        return sum;
    }
}

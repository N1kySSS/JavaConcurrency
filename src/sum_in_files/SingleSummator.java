package sum_in_files;

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
                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());;
            }
        }

        return sum;
    }
}

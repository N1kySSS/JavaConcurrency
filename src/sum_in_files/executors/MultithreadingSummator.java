package sum_in_files.executors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MultithreadingSummator {

    protected volatile int sum = 0;
    private static final int THREAD_NUMBER = 8;

    private synchronized void adding(int value) {
        sum += value;
    }

    private int sumFilesRange(File[] files, int start, int end) {
        int sumInFiles = 0;

        for (int i = start; i < end; i++) {
            try (Scanner scanner = new Scanner(files[i])) {
                while (scanner.hasNextLine()) {
                    int number = Integer.parseInt(scanner.nextLine());
                    sumInFiles += number;
                }
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println(fileNotFoundException.getMessage());
            }
        }

        return sumInFiles;
    }

    public int sumFiles(String directoryPath) {
        File directory = new File(directoryPath);

        File[] files = directory.listFiles();

        if (files == null) {
            return Integer.MIN_VALUE;
        }

        Thread[] threads = new Thread[THREAD_NUMBER];
        int range = files.length / THREAD_NUMBER;

        for (int i = 0; i < THREAD_NUMBER; i++) {
            int startFileNumber = i * range;
            int endFileNumber = (i == THREAD_NUMBER - 1) ? files.length : (startFileNumber + range);

            threads[i] = new Thread(() -> {
                int sumInRange = sumFilesRange(files, startFileNumber, endFileNumber);
                adding(sumInRange);
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException interruptedException) {
                System.out.println(interruptedException.getMessage());
            }
        }

        return sum;
    }
}

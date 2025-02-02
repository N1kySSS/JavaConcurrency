package sum_in_files;

import sum_in_files.executors.MultithreadingSummator;
import sum_in_files.executors.SingleSummator;
import sum_in_files.util.FileGenerator;
import sum_in_files.util.Saver;

public class EntryPoint {
    private static final int COUNT_FILES = 223;
    private static final String DIRECTORY = "src/sum_in_files/files";
    private static final int LINES_IN_FILES = 99999;

    private static final FileGenerator FILE_GENERATOR = new FileGenerator();
    private static final SingleSummator SINGLE_SUMMATOR = new SingleSummator();
    private static final MultithreadingSummator MULTITHREADING_SUMMATOR = new MultithreadingSummator();

    public static void main(String[] args) {
        clearFiles();
        createAndFill();
        outputSingleThreadResult();
        outputMultiThreadResult();
    }

    public static void createAndFill() {
        FILE_GENERATOR.createFiles(DIRECTORY, COUNT_FILES);
        FILE_GENERATOR.fillFilesWithNumbers(DIRECTORY, COUNT_FILES, LINES_IN_FILES);
    }

    public static void clearFiles() {
        FILE_GENERATOR.clearFileContents(DIRECTORY);
    }

    public static void outputSingleThreadResult() {
        long start = System.currentTimeMillis();
        int result = SINGLE_SUMMATOR.sumAllFiles(DIRECTORY);
        long end = System.currentTimeMillis();
        Saver.save("SingleThread", result, end - start);
    }

    public static void outputMultiThreadResult() {
        long start = System.currentTimeMillis();
        int result = MULTITHREADING_SUMMATOR.sumFiles(DIRECTORY);
        long end = System.currentTimeMillis();
        Saver.save("MultiThread", result, end - start);
    }
}

package sum_in_files;

public class EntryPoint {
    private static final int COUNT_FILES = 223;
    private static final String DIRECTORY = "D:\\Data\\Projects\\JavaConcurrency\\src\\sum_in_files\\files";
    private static final int LINES_IN_FILES = 99999;

    private static final FileGenerator fileGenerator = new FileGenerator();
    private static final SingleSummator summator = new SingleSummator();

    public static void main(String[] args) {
        clearFiles();
        createAndFill();
        outputResult();
    }

    public static void createAndFill() {
        fileGenerator.createFiles(DIRECTORY, COUNT_FILES);
        fileGenerator.fillFilesWithNumbers(DIRECTORY, COUNT_FILES, LINES_IN_FILES);
    }

    public static void clearFiles() {
        fileGenerator.clearFileContents(DIRECTORY);
    }

    public static void outputResult() {
        long start = System.currentTimeMillis();
        int result = summator.sumAllFiles(DIRECTORY);
        long end = System.currentTimeMillis();
        Saver.save("SingleThread", result, end - start);
    }
}

package sum_in_files;

public class EntryPoint {
    private static final int COUNT_FILES = 13;
    private static final String DIRECTORY = "D:\\Data\\Projects\\JavaConcurrency\\src\\sum_in_files\\files";
    private static final int LINES_IN_FILES = 5555;

    public static void main(String[] args) {
        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.createFiles(DIRECTORY, COUNT_FILES);

        fileGenerator.fillFilesWithNumbers(DIRECTORY, COUNT_FILES, LINES_IN_FILES);
    }
}

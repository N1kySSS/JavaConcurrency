package parallel_minor.executors;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelExecutor {

    private static int[][] minor(int[][] matrix, int row, int column) {
        int n = matrix.length;
        int[][] minorMatrix = new int[n - 1][n - 1];

        int minorRow = 0;

        for (int i = 0; i < n; i++) {
            if (i == row) { continue; }

            int minorColumn = 0;

            for (int j = 0; j < n; j++) {
                if (j == column) { continue; }

                minorMatrix[minorRow][minorColumn] = matrix[i][j];
                minorColumn++;
            }

            minorRow++;
        }

        return minorMatrix;
    }

    public BigInteger calculateDeterminant(int[][] matrix) throws InterruptedException {
        int n = matrix.length;

        if (n == 1) {
            return BigInteger.valueOf(matrix[0][0]);
        }

        if (n == 2) {
            return BigInteger.valueOf(matrix[0][0])
                    .multiply(BigInteger.valueOf(matrix[1][1]))
                    .subtract(BigInteger.valueOf(matrix[0][1])
                            .multiply(BigInteger.valueOf(matrix[1][0])));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ArrayList<Runnable> tasks = new ArrayList<>();
        ArrayList<BigInteger> results = new ArrayList<>();

        CountDownLatch countDownLatch = new CountDownLatch(n);

        for (int column = 0; column < n; column++) {
            final int finalColumn = column;
            tasks.add(() -> {
                try {
                    int[][] minorMatrix = minor(matrix, 0, finalColumn);
                    BigInteger determinant = calculateDeterminant(minorMatrix);
                    BigInteger coefficient = (finalColumn % 2 == 0) ? BigInteger.valueOf(1) : BigInteger.valueOf(-1);

                    synchronized (results) {
                        results.add(coefficient
                                .multiply(BigInteger.valueOf(matrix[0][finalColumn]))
                                .multiply(determinant));
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }

            });
        }

        for (Runnable task : tasks) {
            executorService.submit(task);
        }

        countDownLatch.await();

        BigInteger result = BigInteger.ZERO;

        for (BigInteger partOfResult: results) {
            result.add(partOfResult);
        }

        return result;
    }
}

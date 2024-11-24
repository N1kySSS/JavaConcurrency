package parallel_minor.executors;

import java.math.BigInteger;

public class SingleThreadExecutor {

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

    public BigInteger calculateDeterminant(int[][] matrix) {
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

        BigInteger result = BigInteger.ZERO;

        for (int column = 0; column < n; column++) {
            result = result.add(BigInteger.valueOf((long) Math.pow(-1, column))
                    .multiply(BigInteger.valueOf(matrix[0][column]))
                    .multiply(calculateDeterminant(minor(matrix, 0, column))));
        }

        return result;
    }
}

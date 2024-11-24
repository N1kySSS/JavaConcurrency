package parallel_minor.executors;

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

    public long calculateDeterminant(int[][] matrix) {
        int n = matrix.length;

        if (n == 1) {
            return matrix[0][0];
        }

        if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        int result = 0;

        for (int column = 0; column < n; column++) {
            result += (long) Math.pow(-1, column) * matrix[0][column] *
                    calculateDeterminant(minor(matrix, 0, column));
        }

        return result;
    }
}

package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RowColSum {
    private static final Logger LOG = LoggerFactory.getLogger(RowColSum.class.getName());

    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums() {
        }

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

    }

    public static Sums[] sum(int[][] matrix) {
        int length = matrix.length;
        Sums[] sums = new Sums[length];
        int rowSum;
        int colSum;
        for (int i = 0; i < length; i++) {
            rowSum = 0;
            colSum = 0;
            for (int j = 0; j < length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
                /**
                 * Добавил задержку, чтобы имитировать нагрузку.
                 */
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            sums[i] = new Sums(rowSum, colSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int length = matrix.length;
        Sums[] sums = new Sums[length];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < length; i++) {
            futures.put(i, getSums(matrix, i));
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    private static CompletableFuture<Sums> getSums(int[][] matrix, int dimension) {
        return CompletableFuture.supplyAsync(
                () -> {
                    int rowSum = 0;
                    int colSum = 0;
                    for (int j = 0; j < matrix.length; j++) {
                        rowSum += matrix[dimension][j];
                        colSum += matrix[j][dimension];
                        /**
                         * Добавил задержку, чтобы имитировать нагрузку.
                         */
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    LOG.debug(Thread.currentThread().getName() + " complete.");
                    return new Sums(rowSum, colSum);
                }
        );
    }
}

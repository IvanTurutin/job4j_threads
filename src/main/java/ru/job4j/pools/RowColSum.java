package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RowColSum {
    private static final Logger LOG = LoggerFactory.getLogger(RowColSum.class.getName());

    public static Sums[] sum(int[][] matrix) {
        int length = matrix.length;
        Sums[] sums = new Sums[length];
        for (int i = 0; i < length; i++) {
            sums[i] = dimensionSum(matrix, i);
        }
        return sums;
    }

    private static Sums dimensionSum(int[][] matrix, int dimension) {
        int rowSum = 0;
        int colSum = 0;
        for (int j = 0; j < matrix.length; j++) {
            rowSum += matrix[dimension][j];
            colSum += matrix[j][dimension];
        }
        LOG.debug(Thread.currentThread().getName() + " complete.");
        return new Sums(rowSum, colSum);

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
        return CompletableFuture.supplyAsync(() -> dimensionSum(matrix, dimension));
    }
}

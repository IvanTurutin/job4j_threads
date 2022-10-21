package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void sum() {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        long timeBefore = System.currentTimeMillis();
        RowColSum.Sums[] sums = RowColSum.sum(matrix);
        long timeAfter = System.currentTimeMillis();
        assertThat(sums[0].getColSum()).isEqualTo(28);
        assertThat(sums[0].getRowSum()).isEqualTo(10);
        assertThat(sums[1].getColSum()).isEqualTo(32);
        assertThat(sums[1].getRowSum()).isEqualTo(26);
        assertThat(sums[2].getColSum()).isEqualTo(36);
        assertThat(sums[2].getRowSum()).isEqualTo(42);
        assertThat(sums[3].getColSum()).isEqualTo(40);
        assertThat(sums[3].getRowSum()).isEqualTo(58);
        System.out.println("elapsed time = " + (timeAfter - timeBefore));
    }

    @Test
    void asyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        long timeBefore = System.currentTimeMillis();
        RowColSum.Sums[] sums = RowColSum.asyncSum(matrix);
        long timeAfter = System.currentTimeMillis();
        assertThat(sums[0].getColSum()).isEqualTo(28);
        assertThat(sums[0].getRowSum()).isEqualTo(10);
        assertThat(sums[1].getColSum()).isEqualTo(32);
        assertThat(sums[1].getRowSum()).isEqualTo(26);
        assertThat(sums[2].getColSum()).isEqualTo(36);
        assertThat(sums[2].getRowSum()).isEqualTo(42);
        assertThat(sums[3].getColSum()).isEqualTo(40);
        assertThat(sums[3].getRowSum()).isEqualTo(58);
        System.out.println("elapsed time = " + (timeAfter - timeBefore));

    }
}
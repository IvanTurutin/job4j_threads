package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FindIndexTest {

    @Test
    void whenTenElementsIntegerArray() {
        Integer[] array = new Integer[] {10, 2, 9, 2, 8, 3, 7, 4, 6, 5};
        int result = FindIndex.find(array, 2);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void whenTenElementsIntegerArrayAndNotFound() {
        Integer[] array = new Integer[] {10, 1, 9, 2, 8, 3, 7, 4, 6, 5};
        int result = FindIndex.find(array, 22);
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void whenTenElementsStringArray() {
        String[] array = new String[] {"10", "2", "9", "2", "8", "3", "7", "4", "6", "5"};
        int result = FindIndex.find(array, "2");
        assertThat(result).isEqualTo(1);
    }

    @Test
    void whenTenElementsStringArrayAndNotFound() {
        String[] array = new String[] {"10", "1", "9", "2", "8", "3", "7", "4", "6", "5"};
        int result = FindIndex.find(array, "22");
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void whenMoreThanTenElementsStringArray() {
        String[] array = new String[] {
                "10", "1", "9", "2", "8", "3", "7", "4", "6", "5",
                "10", "1", "9", "2", "8", "3", "7", "4", "6",
                "10", "1", "9", "2", "8", "3", "7",
                "10", "1", "9", "2", "8", "3",
                "10", "1", "9", "2", "8",
                "10", "1", "9", "2", "8", "3", "7", "4", "6", "5",
                "10", "1", "9", "2", "8", "3", "7", "4", "6",
                "10", "1", "9", "2", "8", "3", "7",
                "10", "1", "9", "2", "8", "3",
                "10", "1", "9", "2", "8",
                "10", "1", "9", "2", "8", "3", "7", "4", "6", "5",
                "10", "1", "9", "2", "8", "3", "7", "4", "6",
                "10", "1", "9", "2", "8", "3", "7",
                "10", "1", "9", "2", "8", "3",
                "10", "1", "9", "2", "8", "151"
        };
        int result = FindIndex.find(array, "1");
        assertThat(result).isEqualTo(107);
    }

    @Test
    void whenMoreThanTenElementsStringArrayNotFound() {
        String[] array = new String[] {
                "10", "1", "9", "2", "8", "3", "7", "4", "6", "5",
                "10", "1", "9", "2", "8", "3", "7", "4", "6",
                "10", "1", "9", "2", "8", "3", "7",
                "10", "1", "9", "2", "8", "3",
                "10", "1", "9", "2", "8",
                "10", "1", "9", "2", "8", "3", "7", "4", "6", "5",
                "10", "1", "9", "2", "8", "3", "7", "4", "6",
                "10", "1", "9", "2", "8", "3", "7",
                "10", "1", "9", "2", "8", "3",
                "10", "1", "9", "2", "8",
                "10", "1", "9", "2", "8", "3", "7", "4", "6", "5",
                "10", "1", "9", "2", "8", "3", "7", "4", "6",
                "10", "1", "9", "2", "8", "3", "7",
                "10", "1", "9", "2", "8", "3",
                "10", "1", "9", "2", "8"
        };
        int result = FindIndex.find(array, "22");
        assertThat(result).isEqualTo(-1);
    }

}

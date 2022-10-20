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
        Integer[] array = new Integer[] {10, 1, 9, 2, 8, 3, 7, 4, 6, 5};
        int result = FindIndex.find(array, 2);
        assertThat(result).isEqualTo(3);
    }

    @Test
    void whenTenElementsIntegerArrayAndNotFound() {
        Integer[] ints = new Integer[] {10, 1, 9, 2, 8, 3, 7, 4, 6, 5};
        List<Integer> array = Arrays.asList(ints);
        FindIndex<Integer> findIndex = new FindIndex<>(array, 11, 0, array.size() - 1);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<Integer> results = forkJoinPool.invoke(findIndex);
        assertTrue(results.isEmpty());
    }

    @Test
    void whenTenElementsStringArray() {
        String[] strings = new String[] {"10", "1", "9", "2", "8", "3", "7", "4", "6", "5"};
        List<String> array = Arrays.asList(strings);
        FindIndex<String> findIndex = new FindIndex<>(array, "2", 0, array.size() - 1);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<Integer> results = forkJoinPool.invoke(findIndex);
        assertFalse(results.isEmpty());
        assertThat(results.get(0)).isEqualTo(3);
    }

    @Test
    void whenTenElementsStringArrayAndNotFound() {
        String[] strings = new String[] {"10", "1", "9", "2", "8", "3", "7", "4", "6", "5"};
        List<String> array = Arrays.asList(strings);
        FindIndex<String> findIndex = new FindIndex<>(array, "11", 0, array.size() - 1);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<Integer> results = forkJoinPool.invoke(findIndex);
        assertTrue(results.isEmpty());
    }

    @Test
    void whenMoreThanTenElementsStringArray() {
        String[] strings = new String[] {"10", "1", "9", "2", "8", "3", "7", "4", "6", "5",
                "10", "1", "9", "2", "8", "3", "7", "4", "6",
                "10", "1", "9", "2", "8", "3", "7",
                "10", "1", "9", "2", "8", "3",
                "10", "1", "9", "2", "8",
        };
        List<String> array = Arrays.asList(strings);
        FindIndex<String> findIndex = new FindIndex<>(array, "2", 0, array.size() - 1);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<Integer> results = forkJoinPool.invoke(findIndex);
        System.out.println(results);
        assertFalse(results.isEmpty());
        assertThat(results.get(0)).isEqualTo(3);
        assertThat(results.get(1)).isEqualTo(13);
        assertThat(results.get(2)).isEqualTo(22);
        assertThat(results.get(3)).isEqualTo(29);
        assertThat(results.get(4)).isEqualTo(35);
    }

}

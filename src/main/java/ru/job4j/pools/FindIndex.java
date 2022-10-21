package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.lang.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FindIndex<T> extends RecursiveTask<Integer> {
    private final T[] dataArray;
    private final T target;
    private final int from;
    private final int to;
    private final static int NOT_FOUND = -1;
    private final static int TASK_LENGTH = 10;
    private static final Logger LOG = LoggerFactory.getLogger(FindIndex.class.getName());

    public FindIndex(T[] dataArray, T target, int from, int to) {
        this.dataArray = dataArray;
        this.target = target;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= TASK_LENGTH) {
            return findIndex();
        }

        int mid = (from + to) / 2;
        FindIndex<T> leftSide = new FindIndex<>(dataArray, target, from, mid);
        FindIndex<T> rightSide = new FindIndex<>(dataArray, target, mid + 1, to);
        leftSide.fork();
        rightSide.fork();

        return Math.max(leftSide.join(), rightSide.join());
    }

    private int findIndex() {
        LOG.debug(Thread.currentThread().getName() + ": time = " + System.currentTimeMillis());
        for (int i = from; i <= to; i++) {
            if (Objects.equals(dataArray[i], target)) {
                LOG.debug(Thread.currentThread().getName() + ": target found with index = " + i);
                return i;
            }
        }
        LOG.debug(Thread.currentThread().getName() + ": target not found");
        return NOT_FOUND;
    }

    public static <T> Integer find(T[] array, T target) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FindIndex<T> findIndex = new FindIndex<>(array, target, 0, array.length - 1);
        return forkJoinPool.invoke(findIndex);
    }
}

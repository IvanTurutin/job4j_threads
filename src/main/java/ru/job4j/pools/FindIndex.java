package ru.job4j.pools;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;
import java.lang.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.concurrent.Wget;


public class FindIndex<T> extends RecursiveTask<Integer> {
    private final List<T> dataArray;
    private final T target;
    private final int from;
    private final int to;
    private final static Integer NOT_FOUND = -1;
    private static final Logger LOG = LoggerFactory.getLogger(Wget.class.getName());

    public FindIndex(List<T> dataArray, T target, int from, int to) {
        this.dataArray = dataArray;
        this.target = target;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return findIndex();
        }

        int mid = (from + to) / 2;
        FindIndex<T> leftSide = new FindIndex<>(dataArray, target, from, mid);
        FindIndex<T> rightSide = new FindIndex<>(dataArray, target, mid + 1, to);
        leftSide.fork();
        rightSide.fork();

        int tmp;
        tmp = leftSide.join();
        if (tmp >= 0) {
            return tmp;
        }

        tmp = rightSide.join();
        if (tmp >= 0) {
            return tmp;
        }

        return NOT_FOUND;
    }

    private Integer findIndex() {
        LOG.debug(Thread.currentThread().getName() + ": time = " + System.currentTimeMillis());
        for (int i = from; i <= to; i++) {
            if (Objects.equals(dataArray.get(i), target)) {

                LOG.debug(Thread.currentThread().getName() + ": target found with index = " + i);
                return i;
            }
        }
        LOG.debug(Thread.currentThread().getName() + ": target not found");
        return NOT_FOUND;
    }

    public static <T> Integer find(T[] array, T target) {
        List<T> dataArray = Arrays.asList(array);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FindIndex<T> findIndex = new FindIndex<>(dataArray, target, 0, dataArray.size() - 1);
        return forkJoinPool.invoke(findIndex);
    }
}

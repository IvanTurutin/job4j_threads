package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.RecursiveTask;

public class FindIndex<T> extends RecursiveTask<List<Integer>> {

    private final List<T> dataArray;
    private final T target;
    private final int from;
    private final int to;
    private final List<Integer> results = new ArrayList<>();

    public FindIndex(List<T> dataArray, T target, int from, int to) {
        this.dataArray = dataArray;
        this.target = target;
        this.from = from;
        this.to = to;
    }

    @Override
    protected List<Integer> compute() {
        if (to - from <= 10) {
            for (int i = from; i <= to; i++) {
                if (Objects.equals(dataArray.get(i), target)) {
                    results.add(i);
                }
            }
            return results;
        }

        int mid = (from + to) / 2;
        FindIndex<T> leftSide = new FindIndex<>(dataArray, target, from, mid);
        FindIndex<T> rightSide = new FindIndex<>(dataArray, target, mid + 1, to);
        leftSide.fork();
        rightSide.fork();
        results.addAll(leftSide.join());
        results.addAll(rightSide.join());
        return results;
    }
}

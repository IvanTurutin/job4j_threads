package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@ThreadSafe
public final class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = new ArrayList<>(list);
    }

    public SingleLockList() {
        this.list = new ArrayList<>();
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    public synchronized List<T> copy(List<T> dest) {
        return new ArrayList<>(dest);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }
}

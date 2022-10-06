package ru.job4j.concurrent;

public class Count {
    private int value;

    public void increment() {
        this.value++;
    }

    public int get() {
        return this.value;
    }
}

package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        String ls = System.lineSeparator();
        Runnable lambda = () -> System.out.printf("TreadName = %s%s", Thread.currentThread().getName(), ls);
        Thread first = new Thread(lambda);
        Thread second = new Thread(lambda);
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println("Thread is not terminated.");
        }
        System.out.println("All threads are terminated");
    }
}

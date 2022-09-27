package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        String ls = System.lineSeparator();
        Runnable lambda = () -> System.out.printf("TreadName = %s%s", Thread.currentThread().getName(), ls);
        Thread first = new Thread(lambda);
        Thread second = new Thread(lambda);
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED && second.getState() != Thread.State.TERMINATED) {
            Thread.currentThread().sleep(100);
        }
        System.out.println("All threads are terminated");
    }
}

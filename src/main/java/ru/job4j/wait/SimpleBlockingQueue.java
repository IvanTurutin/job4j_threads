package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int queueLength;

    public SimpleBlockingQueue(int queueLength) {
        this.queueLength = queueLength;
    }

    public SimpleBlockingQueue() {
        this.queueLength = 10;
    }

    public void offer(T value) {
        synchronized (this) {
            while (queue.size() >= queueLength) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            queue.add(value);
            this.notifyAll();
/*
            System.out.println("producer = " + value);
*/
        }
    }

    public T poll() {
        synchronized (this) {
            while (queue.size() < 1) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            T rslt = queue.poll();
            this.notifyAll();
/*
            System.out.println("consumer = " + rslt);
*/
            return rslt;
        }
    }
}

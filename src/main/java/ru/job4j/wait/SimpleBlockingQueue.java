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
        this.queueLength = Integer.MAX_VALUE;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() >= queueLength) {
                this.wait();
            }
            queue.add(value);
            this.notifyAll();
/*
            System.out.println("producer = " + value);
*/
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty()) {
                this.wait();
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

package ru.job4j.wait;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        LinkedList<Integer> rslt = new LinkedList<>();
        List<Integer> expect = Arrays.asList(0, 1, 2, 3, 4);
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(1);
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {

                rslt.add(simpleBlockingQueue.poll());
            }
        });
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                simpleBlockingQueue.offer(i);
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(rslt).isEqualTo(expect);
    }
}
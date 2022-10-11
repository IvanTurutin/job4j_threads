package ru.job4j.wait;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        LinkedList<String> rslt = new LinkedList<>();
        List<String> expect = Arrays.asList(
                "producer = 0", "consumer = 0",
                "producer = 1", "consumer = 1",
                "producer = 2", "consumer = 2"
                );
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(1);
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                simpleBlockingQueue.poll();
                rslt.add("consumer = " + i);
            }
        });
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                simpleBlockingQueue.offer(i);
                rslt.add("producer = " + i);
            }
        });
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        assertThat(rslt).isEqualTo(expect);
    }
}
package ru.job4j.nonblock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    public void whenMultiThreadCounter() throws InterruptedException {
        int numberOfThreads = 1000;
        ExecutorService service = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        CASCount counter = new CASCount();
        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                counter.increment();
                latch.countDown();
            });
        }
        latch.await();
        assertThat(numberOfThreads).isEqualTo(counter.get());
    }


    @Test
    public void whenOneThreadCouner() {
        int numbOfCount = 1000;
        CASCount counter = new CASCount();
        for (int i = 0; i < numbOfCount; i++) {
            counter.increment();
        }
        assertThat(numbOfCount).isEqualTo(counter.get());
    }
}
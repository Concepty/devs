package dev.devs;

import lombok.SneakyThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class AsyncParser extends TSVParser{

    BlockingDeque<String[]> recordQueue;
    Thread producer;
    List<Thread> consumers;

    public AsyncParser(String filePath) throws IOException {
        super(filePath);
        recordQueue = new LinkedBlockingDeque<>(300);
        producer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(!isClosed()) {
                        produce();
                    }
                } catch (InterruptedException inte) {
                    throw new RuntimeException(inte);
                }
            }
        });
        consumers = new ArrayList<>();
    }
    private void produce() throws InterruptedException {
        String[] cols = parseLine();
        if (cols != null) recordQueue.put(cols);
    }
    @SneakyThrows
    public String[] poll() {
        String[] cols = null;
        while (cols == null && !isClosed()) {
            cols = recordQueue.poll(1, TimeUnit.MILLISECONDS);
        }
        return cols;
    }
    @Override
    public boolean isClosed() {
        return super.isClosed() && recordQueue.size() == 0;
    }
    public void runProducer() throws InterruptedException {
        producer.start();
    }

    /**
     * Client defines runnable.
     * For this reason, poll (and further consuming methods) need to be
     * public and thus encapsulation is compromised.
     */
    // Client defines runnable task.
    public void runConsumer(Runnable runnable) throws InterruptedException {
        Thread consumer = new Thread(runnable);
        consumers.add(consumer);
        consumer.start();
    }

    @SneakyThrows
    public void join() {
        producer.join();
        consumers.forEach(c -> {
            try {
                c.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }




}

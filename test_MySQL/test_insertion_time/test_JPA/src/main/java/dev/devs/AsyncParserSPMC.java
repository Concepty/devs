package dev.devs;

import lombok.SneakyThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// Single Provider Multi Consumer
public class AsyncParserSPMC extends TSVParser {

    final static int size = 300;
    private BlockingDeque<String[]> recordQueue;
    private Thread producer;
    private List<Thread> consumers;
    static private AtomicInteger emptyCount= new AtomicInteger(0);
    static private AtomicInteger fullCount = new AtomicInteger(0);



    // TODO: change in parent class, Additional constructor which allows
    // TODO: to choose whether ignore the first line or not
    // TODO: consider if I need to change all descending classes or not and Do
    public AsyncParserSPMC(String filePath) throws IOException {
        super(filePath);
        recordQueue = new LinkedBlockingDeque<>(size);
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
        if (cols != null) {
            if (recordQueue.size() == size) fullCount.getAndIncrement();
            recordQueue.put(cols);
        }
    }
    @SneakyThrows
    public String[] poll() {
        String[] cols = null;
        while (cols == null && !isClosed()) {
            if (recordQueue.size() == 0) emptyCount.getAndIncrement();
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
     * Client defines runnable (functor).
     * For this reason, poll (and further consuming methods) need to be
     * public and thus encapsulation is compromised.
     */
    // Client defines runnable task.
    public void runConsumer(Runnable runnable) throws InterruptedException {
        Thread consumer = new Thread(runnable);
        consumers.add(consumer);
        consumer.start();
    }
    // TODO: 기존에는 IMDbOperation의 runUpdate가 private이라서 IMDbOperation의 context에 위치하는 closure를 이용해서 처리해야 했는데, 이제 그럴 필요 없이, SPMC에서 해결하도록 수정하자.

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

        System.out.println("queue was empty : " + emptyCount + " times");
        System.out.println("queue was full : " + fullCount + " times");
    }




}

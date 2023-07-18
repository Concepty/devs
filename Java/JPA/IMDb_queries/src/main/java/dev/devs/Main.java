package dev.devs;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Instant start = Instant.now();
//        by1();
//        byNum(1000);
        asyncInsert(100, 1);
        Instant end = Instant.now();
        System.out.println("bulk insertion finished in : " + Long.toString(Duration.between(start, end).getSeconds()) + " seconds");

    }

    public static void byNum(int records) {
        IMDbParser parser;

        try {
            parser = new IMDbParser("title.ratings");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IMDbOperation.getInstance().insertByNumRecords(records, parser);
    }
    public static void asyncInsert(int records, int threads) {
        IMDbAsyncParser parser;

        try {
            parser = new IMDbAsyncParser("title.ratings");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("producer thread running...");
                    parser.keepProduce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        producer.start();

        ArrayList<Thread> threadPool = new ArrayList<>(threads);
        //TODO
        for (int i=0; i < threads; i++) {
            System.out.println("bbb");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("consumer thread running...");
                    IMDbOperation.getInstance().insertAsync(records, parser);
                }
            });
            threadPool.add(t);
            t.start();
        }

        System.out.println("thread running...");
        try {
            producer.join();
            for (Thread t: threadPool) {
                t.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



//        Runnable producerThread = () -> {
//            try {
//                producer.keepProduce();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        };
//        producerThread.run();
//
//        // TODO thread pool which create thread if needed
//        // TODO decide with remainingCapacity
//
//        ArrayList<Runnable> consumerPool = new ArrayList<>(5);
//        consumerPool.forEach((consumer) -> {
//            consumer = () -> {
//
//            };
//        });

    }
}
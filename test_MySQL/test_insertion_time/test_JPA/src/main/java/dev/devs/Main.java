package dev.devs;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
//        insertNumRTs(5000);
        insertNumRTsAsync(100000, 3);
    }



    private interface Method {
        public void run();
    }

    public static void runWithTimer(Method method) {
        Instant start = Instant.now();
        method.run();
        Instant end = Instant.now();
        System.out.println("insertion finished in :" + Long.toString(Duration.between(start, end). getSeconds()) + " seconds");
    }

    /**
     * persist row by row,commit per unit
     * @param unit
     */
    public static void insertNumRTs(int unit) {
        runWithTimer(() -> {
            TSVParser parser;

            try {
                parser = new TSVParser("title.ratings.tsv");
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
            IMDbOperations.insertRTPerUnit(unit, parser);
        });
    }
    public static void insertNumRTsAsync(int unit, int threads) {
        runWithTimer(() -> {
            AsyncParser parser;

            try {
                parser = new AsyncParser("title.ratings.tsv");
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
            IMDbOperations.insertRTPerUnitAsync(unit, threads, parser);
        });
    }
}
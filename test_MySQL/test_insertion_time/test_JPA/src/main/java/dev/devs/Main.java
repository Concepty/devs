package dev.devs;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
//        insertNumRTs(5000);
//        insertNumTRsAsync(100000, 3);
        insertTRsByJPQL("insert_by_100.sql");
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
    public static void insertNumTRs(int unit) {
        runWithTimer(() -> {
            TSVParser parser;

            try {
                parser = new TSVParser("title.ratings.tsv");
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
            IMDbOperations.insertTRPerUnit(unit, parser);
        });
    }
    public static void insertNumTRsAsync(int unit, int threads) {
        runWithTimer(() -> {
            AsyncParser parser;

            try {
                parser = new AsyncParser("title.ratings.tsv");
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
            IMDbOperations.insertTRPerUnitAsync(unit, threads, parser);
        });
    }

    public static void insertTRsByJPQL(String queryFile) {
        runWithTimer(() -> {
            SQLParser parser;

            try {
                parser = new SQLParser(queryFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            IMDbOperations.insertTRsByJPQL(parser);
        });
    }
}
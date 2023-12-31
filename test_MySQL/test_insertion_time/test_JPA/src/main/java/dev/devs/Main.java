package dev.devs;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
//        insertNumTRs(5);
        insertNumTRsAsync(50, 20);
//        persistNumTRsAsync(1, 1);
//        insertTRsByJPQL("insert_by_100.sql");
//        try {
//            new AsyncParserMPMC(false, 5);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        insertTRsInSplit(5,5);
    }

    private interface Method {
        public void run();
    }

    public static void runWithTimer(Method method) {
        Instant start = Instant.now();
        method.run();
        Instant end = Instant.now();
        System.out.println("insertion finished in :" + Duration.between(start, end).toMillis() + " milliseconds");
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
            AsyncParserSPMC parser;

            try {
                parser = new AsyncParserSPMC("title.ratings.tsv");
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
            IMDbOperations.insertTRPerUnitAsync(unit, threads, parser);
        });
    }

    public static void persistNumTRsAsync(int unit, int threads) {
        runWithTimer(() -> {
            AsyncParserSPMC parser;

            try {
                parser = new AsyncParserSPMC("title.ratings.tsv");
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
            IMDbOperations.persistTRPerUnitAsync(unit, threads, parser);
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

    public static void insertTRsInSplit(int unit, int threads) {
        runWithTimer(() -> {
            AsyncParserMPMC parser;

            try {
                parser = new AsyncParserMPMC(false, threads);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                parser.startThreads(unit);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
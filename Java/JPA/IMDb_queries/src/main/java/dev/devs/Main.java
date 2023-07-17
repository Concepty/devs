package dev.devs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {

        Instant start = Instant.now();
//        by1();
        byNum(5);
        Instant end = Instant.now();
        System.out.println("bulk insertion finished in : " + Long.toString(Duration.between(start, end).getSeconds()) + " seconds");

    }

    public static void by1() {
        IMDbParser parser;
        IMDbTransaction transaction = new IMDbTransaction();
        try {
            parser = new IMDbParser("title.ratings");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        parser.parseFirstLine();
        transaction.insertByOneRecords(parser);
    }

    public static void byNum(int num) {
        IMDbParser parser;
        IMDbTransaction transaction = new IMDbTransaction();
        try {
            parser = new IMDbParser("title.ratings");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        parser.parseFirstLine();
        transaction.insertByNumRecords(num, parser);
    }

//    public static void importTitleRating () {
//        // TODO: add simple check word to confirm importing task
//
//        String tsvPath = "~/devs/Test_Data_Set/title.ratings.tsv";
//
//        try {
//            FileInputStream inputStream = new FileInputStream(tsvPath);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//
//
//    }
}
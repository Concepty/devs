package dev.devs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        new IMDbTransaction().testRunQuery();
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
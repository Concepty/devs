package dev.devs;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IMDbParser {
    final private static String tsvDirPath;
    private static Map<String, Class<?>> tableMap = new HashMap<>();
    private BufferedReader tsvReader;
    private int readLines;

    private boolean EOF;


    static {
        tsvDirPath = "/Users/hwansu/devs/Test_Data_Set/";
        // file - table mapping
        tableMap.put("title.ratings", IMDbTable.TitleRating.class);
    }

    // TODO: As inserting bulky records(~6.51GB total) I need to use Threads.
    // TODO: Problems using Thread is
    // TODO: 1. Thread safety of session related objects (hibernate Session,
    // TODO: JPA EntityManager, JPA EntityTransaction)
    // TODO: 2. How to keep the order of data records (not required. extra
    // TODO: challenge.)
    // TODO: -> use Single Provider Multi Consumer (Parsing would be much
    // TODO: faster)
    // TODO: 2.1. How many connections do I need not to let queue overflow
    // TODO: -> Test needed
    // TODO: 3. (Extra Study) How does connection pool works in thread safe
    // TODO: way?
    // TODO:
    // TODO:
    // TODO:

    public IMDbParser(String filePath) throws FileNotFoundException {
        tsvReader = new BufferedReader(new FileReader(tsvDirPath + filePath + ".tsv"));
        readLines = 0;
        EOF = false;
    }
    public boolean isClosed() {
        return EOF;
    }
    public void parseFirstLine() {
        try {
            tsvReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // TODO: need common base class of tables
    public IMDbTable.ParsableTable parseOneLine() {
        String line;
        IMDbTable.TitleRating titleRating = null;
        try {
            line = tsvReader.readLine();
        } catch (IOException e) {
            System.out.println("Number of lines read: " + Integer.toString(readLines));
            throw new RuntimeException(e);
        }
        if (line != null) {
            String[] cols = line.split("\t");
            titleRating = new IMDbTable.TitleRating(cols[0], cols[1], Integer.parseInt(cols[2]));
        } else {
            EOF = true;
            return null;
        }
        return titleRating;
    }

}

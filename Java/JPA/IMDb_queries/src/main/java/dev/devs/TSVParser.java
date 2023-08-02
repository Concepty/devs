package dev.devs;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Responsibility: handle IMDb provided tsv files.
 * parse each line (or multiple lines) and return String[]
 */

public class TSVParser {
    //IMDbSpecific Implementation
    final private static String tsvDirPath = "/Users/hwansu/devs/Test_Data_Set/";
    protected BufferedReader tsvReader;
    protected int readLines;
    protected boolean closed;

    public TSVParser(String filePath) throws IOException {
        tsvReader = new BufferedReader(new FileReader(tsvDirPath + filePath + ".tsv"));
        readLines = 0;
        closed = false;
        // TSV specific implementation
        tsvReader.readLine();
    }

    public boolean isClosed() {
        return closed;
    }

    // TODO: need common base class of tables
    // TODO: is bulk parsing required for performance sake?
    public String[] parseOneLine() {
        String line;
        try {
            line = tsvReader.readLine();
        } catch (IOException e) {
            System.out.println("Number of lines read: " + Integer.toString(readLines));
            throw new RuntimeException(e);
        }
        if (line != null) {
            String[] cols = line.split("\t");
            return cols;
        } else {
            closed = true;
            return null;
        }
    }
}

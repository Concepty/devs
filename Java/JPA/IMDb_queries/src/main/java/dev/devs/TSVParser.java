package dev.devs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Responsibility: handle IMDb provided tsv files.
 * parse each line (or multiple lines) and return String[]
 */

public class TSVParser {
    final private static String tsvDirPath;
    protected BufferedReader tsvReader;
    protected int readLines;
    private boolean EOF;
    static {
        // IMDb specific implementation
        tsvDirPath = "/Users/hwansu/devs/Test_Data_Set/";
        // file - table mapping
    }
    public TSVParser(String filePath) throws IOException {
        tsvReader = new BufferedReader(new FileReader(tsvDirPath + filePath + ".tsv"));
        readLines = 0;
        EOF = false;
        // TSV specific implementation
        tsvReader.readLine();
    }

    public boolean isClosed() {
        return EOF;
    }

    // TODO: need common base class of tables
    // TODO: is bulk parsing required for performance sake?
    public String[] parseOneLine() {
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
            return cols;
        } else {
            EOF = true;
            return null;
        }
    }
}

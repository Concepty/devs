package dev.devs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TSVParser {
    final private static String tsvDirPath = "/Users/hwansu/devs/Test_Data_Set/";
    protected BufferedReader tsvReader;
    private boolean closed;
    private boolean ignoreTheFirstLine = true;


    public TSVParser(String filePath, boolean ignoreTheFirstLine) throws IOException {
        this.ignoreTheFirstLine =  ignoreTheFirstLine;
        try {
            tsvReader = new BufferedReader(new FileReader(filePath));
        } catch (IOException ioe) {
            tsvReader = new BufferedReader(new FileReader(tsvDirPath + filePath));
        }
        closed = false;

        // Ignoring the first line of the TSV file
        tsvReader.readLine();
    }
    public TSVParser(String filePath) throws IOException {
        this(filePath, true);
    }

    public boolean isClosed() { return closed; }

    public String[] parseLine() {
        String line;
        try {
            line = tsvReader.readLine();

        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        if (line == null) {
            closed = true;
            return null;
        }
        String[] cols = line.split("\t");
        return cols;
    }
}

package dev.devs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SQLParser {
    final private static String sqlDirPath = "./src/main/resources/";

    protected BufferedReader sqlReader;
    protected int readLines;
    protected boolean closed;
    public SQLParser(String fileName) throws IOException {
        sqlReader = new BufferedReader(new FileReader(sqlDirPath + fileName));
        readLines = 0;
        closed = false;
    }
    public boolean isClosed() { return closed; }
    public String parseOneLine() {
        String line;
        try {
            line = sqlReader.readLine();
            if (line == null) closed = true;
            else {
                StringBuilder sb = new StringBuilder(line);
                sb.setLength(sb.length() - 1);
                line = sb.toString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;
    }

}

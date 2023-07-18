package dev.devs;

import java.io.IOException;
import java.util.RandomAccess;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// TODO: I don't like this name.
public class IMDbAsyncParser extends IMDbParser{

    BlockingQueue<String[]> recordQueue;

    public IMDbAsyncParser(String filePath) throws IOException {
        super(filePath);
        recordQueue = new LinkedBlockingQueue<>(50);
    }

    // DECISION: queue orm object require more cpu bound work and more
    // memory space as the string read from file not be released when
    // bound by the orm object.
    // As I decided to use single provider and multi consumer, I
    // rather use more threads to create object from string array.
    public void produce() throws InterruptedException {
        // TODO: would it be better to let IMDbParser do the parsing?
        String line;
        try {
            line = tsvReader.readLine();
        } catch (IOException e) {
            System.out.println("Number of lines read: " + Integer.toString(readLines));
            throw new RuntimeException(e);
        }
        if (line != null) {
            recordQueue.put(line.split("\t"));
        }
    }
    public void keepProduce() throws InterruptedException {
        while (!isClosed()) {
            produce();
        }
    }

    public IMDbTable.TitleRating poll() {
        String[] cols = recordQueue.poll();
        return new IMDbTable.TitleRating(cols[0], cols[1], Integer.parseInt(cols[2]));
    }
}

package dev.devs;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// TODO: I don't like this name.
public class IMDbAsyncParser extends IMDbParser{

    BlockingQueue<String[]> recordQueue;
    public static int MAX_CONSUMERS = 100;

    public IMDbAsyncParser(String filePath) throws IOException {
        super(filePath);
        recordQueue = new LinkedBlockingQueue<>(10);
    }

    // DECISION: queue orm object require more cpu bound work and more
    // memory space as the string read from file not be released when
    // bound by the orm object.
    // As I decided to use single provider and multi consumer, I
    // rather use more threads to create object from string array.
    // TODO: Make sure only one thread run keepProduce()
    public void produce() throws InterruptedException {
        String[] cols = parseOneLine();
        if (cols != null) recordQueue.put(cols);
    }
    @Override
    public boolean isClosed() {
        return super.isClosed() && recordQueue.size() == 0;
    }
    public void keepProduce() throws InterruptedException {
        while (!isClosed()) {
            produce();
        }
    }

    public IMDbTable.TitleRating poll() {
        String[] cols = recordQueue.poll();
        if (cols == null) return null;
        return new IMDbTable.TitleRating(cols[0], cols[1], Integer.parseInt(cols[2]));
    }
}

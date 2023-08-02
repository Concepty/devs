package dev.devs;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class PreLoadedParser extends IMDbParser {
    private Queue<IMDbTable.ParsableTable> queue;
    
    public PreLoadedParser(String filePath) throws IOException {
        super(filePath);
        queue = new LinkedList<>();
    }

    public void loadTitleRating() {
        while(!isClosed()) {
            String[] cols = parseOneLine();
            if (cols == null && isClosed()) continue;
            queue.add(new IMDbTable.TitleRating(cols[0], cols[1], Integer.parseInt(cols[2])));
        }
        System.out.println("TitleRating is loaded: " + String.valueOf(queue.size()));
    }

    public IMDbTable.TitleRating poll() {
        return (IMDbTable.TitleRating) queue.poll();
    }


}

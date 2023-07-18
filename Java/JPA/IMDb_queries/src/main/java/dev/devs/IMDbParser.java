package dev.devs;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsibility: parse tsv file and generate Entity object.
 */

public class IMDbParser extends TSVParser{


    public IMDbParser(String filePath) throws IOException {
        super(filePath);
    }

    // TODO: would I create method for each Entity class?
    public IMDbTable.TitleRating generateTitleRatingRecord() {
        String[] cols = parseOneLine();
        if (cols == null) return null;
        return new IMDbTable.TitleRating(cols[0], cols[1], Integer.parseInt(cols[2]));
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



}

package dev.devs;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class IMDbParser {
    final private static String tsvPath;
    private static Map<String, Class<?>> tableMap = new HashMap<>();


    static {
        tsvPath = "~/devs/Test_Data_Set";
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


}

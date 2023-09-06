package dev.devs;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static dev.devs.IMDbOperations.runUpdate;

/**
 * Design consideration.
 * AsyncParserSPMC, AsyncParserMPMC are closely related in design but
 *
 * name error: Provider and Consumer are the same
 */
public class AsyncParserMPMC{
    private List<TSVParser> parsers;
    private List<Thread> threads;
    private int numberOfThreads;

    final private String filePathBase = "split_TR_[N].tsv";
    final private String indexPlaceHolder = "[N]";

    public AsyncParserMPMC(boolean ignoreTheFirstLine, int numberOfThreads) throws IOException {
        if (numberOfThreads<1) throw new RuntimeException("invalid number of threads");
        this.numberOfThreads = numberOfThreads;

        parsers = new ArrayList<>(numberOfThreads);
        threads = new ArrayList<>(numberOfThreads);

        for (int i = 1; i <= numberOfThreads; i++) {
            StringBuilder sb = new StringBuilder(filePathBase);
            int start = sb.indexOf(indexPlaceHolder);
            sb.replace(start, start + indexPlaceHolder.length(), String.valueOf(i));
            parsers.add(new TSVParser(sb.toString(), ignoreTheFirstLine));
        }
    }

    public void startThreads(int commitUnit) throws InterruptedException {
        parsers.forEach(parser->{
           threads.add(new Thread(new Runnable() {
               @Override
               public void run() {
                   while(!parser.isClosed()) {
                       runUpdate(em -> {
                           for (int i = 0; i < commitUnit; i++) {
                               String[] tsvLine = parser.parseLine();
                               if (tsvLine == null) return;
                               em.persist(new IMDbTable.TitleRating(tsvLine));
                           }
                       });
                   }
               }
           }));
        });

        for (Thread t : threads) {
            t.start();
        }


        /**
         * TODO: 기존 AsyncParserSPMC에서 Closure를 이용하여 IMDbOperation의 method와 local 변수를 쓸 수 있었다. 이번에도 마찬가지로 Closure인 Runnable을 전달 받으면 되는데, MPMC의 경우는 parsers가 private이기 때문에 iteration logic을 AsyncParserMPMC에 맡겨야 하는데, 이는 closure의 영역 밖이다. 가장 쉬운 해결책은 MPMC에서 index에 따라서 parser를 전달하는 것인데.. 그렇게 되면 MPMC의 내부 로직이 밖으로 노출된다.
         * TODO: 혹은 runUpdate같은 메소드를 public으로 변경하면 template method를 OOP에서 쓰기에 더 적절하지 않을까?
         * TODO: 결국 OOP라는 것은 object context를 빌려 기능을 수행하고, 그 책임까지 함께 전가하여 개발 과정의 일관성을 갖추는 것인데, (책임이 명확하지 않으면 개발자마다 성향에 따라 달라질테니까.) static이라해도 class context를 가지니까 OOP의 일종이라 볼 수 있지 않을까? 아 object가 아니니까 동적으로 context를 수정하기는 훨씬 어렵겠다.
         * TODO: Runnable이 인자를 받지 못해서 parser하나를 줄 수는 없고..
         */
    }
    public void joinThreads() {
        for (Thread t: threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}

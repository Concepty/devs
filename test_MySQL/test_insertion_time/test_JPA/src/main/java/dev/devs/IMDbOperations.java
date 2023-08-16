package dev.devs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import dev.devs.IMDbTable.*;

public class IMDbOperations {
    private static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("IMDb");
    private interface Transaction {
        public void run(EntityManager em);
    }

    private static void runUpdate(Transaction t) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        t.run(em);
        em.getTransaction().commit();
        em.close();
    }

    public static int insertTRPerUnit(int commitUnit, TSVParser parser) {
        if (commitUnit < 1) return -1;
        while (!parser.isClosed()) {
            runUpdate(em -> {
                for (int i = 0; i < commitUnit; i++) {
                    String[] tsvLine = parser.parseLine();
                    if (tsvLine == null) return;
                    em.persist(new TitleRating(tsvLine));
                }
            });
        }
        return 0;
    }
    public static int insertTRPerUnitAsync(int commitUnit, int consumers, AsyncParser parser) {
        if (commitUnit < 1 || consumers < 1) return -1;
        Runnable consumerRunnable = new Runnable() {
            @Override
            public void run() {
                while(!parser.isClosed()) {
                    runUpdate(em -> {
                        for (int i = 0; i < commitUnit; i++) {
                            String[] tsvLine = parser.poll();
                            if (tsvLine == null) return;
                            em.persist(new TitleRating(tsvLine));
                        }
                    });
                    System.out.println("Committed");
                }
            }
        };
        try {
            parser.runProducer();
            // TODO: can I use i here? i is used in consumer Runnable
            for (int j = 0; j < consumers; j++) {
                parser.runConsumer(consumerRunnable);
            }
            parser.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    public static int insertTRsByJPQL(SQLParser parser) {
        while (!parser.isClosed()) {
            runUpdate(em -> {
                String sql = parser.parseOneLine();
                if (sql == null) return;
                em.createQuery(sql).executeUpdate();
            });
        }
        return 0;
    }
}

package dev.devs;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileNotFoundException;

public class IMDbTransaction {
    static {
        factory = Persistence.createEntityManagerFactory("IMDb");
    }
    private static EntityManagerFactory factory;
    private interface Transaction {
        public void run(EntityManager em);
    }
    // TODO: I need bulk transaction to handle multiple transactions in
    // TODO: one session (EntityManager).
    private void runQuery(Transaction transaction) {
        EntityManager em = factory.createEntityManager();
        transaction.run(em);
        em.close();
    }
    private void runUpdate(Transaction transaction) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        transaction.run(em);
        em.getTransaction().commit();
        em.close();
    }

    public void testRunQuery() {
        runUpdate((em)->{
            IMDbTable.Test test = new IMDbTable.Test("aa23", "hwansu", 23);
            em.persist(test);
        });
    }

    public void insertByOneRecords(IMDbParser parser) {
        while (!parser.isClosed()) {
            // TODO: Do I need to specify the class implementing ParsableTable class?
            IMDbTable.TitleRating tr = (IMDbTable.TitleRating)parser.parseOneLine();
            if (tr != null) {
                runUpdate((em -> {
                    em.persist(tr);
                }));
            }
        }
    }

    public void insertByNumRecords(int num, IMDbParser parser) {
        /*
        * 1327947 records
        * num == 1 : 277 seconds
        * num == 10 : 82 seconds
        * num == 1000 : 57 seconds
        * num == 10000 : 57 seconds
        * num == 100000 : 56 seconds
        * 57 + (277 - 57) / num
        * */
        if (num < 1) return;
        while (!parser.isClosed()) {
            runUpdate((em)->{
                for (int i = 0; i < num; i++) {
                    IMDbTable.TitleRating tr = (IMDbTable.TitleRating)parser.parseOneLine();
                    if (tr != null) {
                        em.persist(tr);
                    } else {
                        return;
                    }
                }
            });
        }
    }
}
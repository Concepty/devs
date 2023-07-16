package dev.devs;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
}
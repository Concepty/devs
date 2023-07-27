package dev.devs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAFunctions {
    private static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("test_Criteria");

    public static void runQuery(Transaction transaction) {
        EntityManager em = factory.createEntityManager();
        transaction.run(em);
        em.close();
    }
    public static void runInsert(Transaction transaction) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        transaction.run(em);
        em.getTransaction().commit();
        em.close();
    }
    public static void runDelete(Transaction transaction) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        transaction.run(em);
        em.getTransaction().commit();
        em.close();
    }

    public interface Transaction {
        public void run(EntityManager em);
    }


}

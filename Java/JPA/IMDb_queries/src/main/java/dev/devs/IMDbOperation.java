package dev.devs;

import jakarta.persistence.*;

public class IMDbOperation {

//    private IMDbOperation() {}
//    private static class Holder {
//        private static final IMDbOperation INSTANCE = new IMDbOperation();
//    }
//    public static IMDbOperation getInstance() {
//        return Holder.INSTANCE;
//    }

    private IMDbOperation() {}
    private static IMDbOperation INSTANCE = new IMDbOperation();

    public static IMDbOperation getInstance() {
        return INSTANCE;
    }

    private static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("IMDb");
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
                    IMDbTable.ParsableTable record = parser.generateTitleRatingRecord();
                    if (record != null) em.persist(record);
                    else return;
                }
            });
        }
    }
    public void insertAsync(int num, IMDbAsyncParser parser) {
        if (num < 1) return;
        while (!parser.isClosed()) {
            runUpdate((em) -> {
                for (int i = 0; i< num; i++) {
                    // TODO: IMDbParser -> entity object,
                    // TODO: IMDbAsyncParser ->
                    IMDbTable.ParsableTable record = parser.poll();
                    if (record != null) em.persist(record);
                    else break;
                }
            });
        }
    }

}
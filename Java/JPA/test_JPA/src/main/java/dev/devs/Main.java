package dev.devs;

public class Main {
    public static void main(String[] args) {
        TestHibernateAPI testHAPI = new TestHibernateAPI();
        testHAPI.printVersion();
        testHAPI.testInsertRecord(3, "hwansu");
    }
}
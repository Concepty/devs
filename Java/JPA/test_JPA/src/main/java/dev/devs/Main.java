package dev.devs;

public class Main {
    public static void main(String[] args) {
        TestHibernate test = new TestHibernate();

        String[] contents = {"aaa", "bbb", "ccc", "ddd"};

        test.insert_transaction(contents);
        test.select_transaction();
//        test.update_transaction();
    }
}
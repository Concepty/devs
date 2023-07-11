package dev.devs;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class IMDbTransaction {
    static {
        // TODO: how to automatically add all table classes?
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(IMDbTable.TitleRating.class)
                .buildSessionFactory();

    }
    private static SessionFactory sessionFactory;


}

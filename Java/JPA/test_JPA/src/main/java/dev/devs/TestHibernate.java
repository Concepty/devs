package dev.devs;

import com.google.protobuf.Message;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.internal.build.AllowSysOut;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import org.hibernate.cfg.Configuration;


public class TestHibernate {

    SessionFactory sessionFactory = null;

    @Entity
    @Table(name="test_CRUD")
    public static class TestEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Getter
        private int id;

        @Column(name="content")
        @Getter @Setter
        private String content;

        public TestEntity() {}
        public TestEntity(String content) { this.content = content; }
    }

    public TestHibernate() {
        /*
        Properties prop = new Properties();
        prop.setProperty("hibernate.connection.url",
                "jdbc:mysql://localhost:3306/test_hibernate");
        prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        prop.setProperty("hibernate.connection.username", "test_hibernate");
        prop.setProperty("hibernate.connection.password", "1234");
        prop.setProperty("hibernate.connection.driver_class",
                "com.mysql.cj.jdbc.Driver");
        prop.setProperty("connection.pool_size", "5");
        prop.setProperty("hibernate.hbm2ddl.auto", "update");
        prop.setProperty("show_sql", "true");
         */


        // TODO: how can hibernate Configuration class is used when there is
        // TODO: java.lang.module.Configuration?
        // TODO: isn't java.lang.* packages are imported automatically?
        Configuration configuration =
                new Configuration()
                    .configure("persistence.xml")
                    .addAnnotatedClass(TestEntity.class);

        // TODO: is this really best?
        // TODO: builder needs conf, and conf needs builder to build?
        StandardServiceRegistryBuilder SSRBuilder =
                new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
        this.sessionFactory =
                configuration.buildSessionFactory(SSRBuilder.build());
    }
    private interface Transaction {
        public int run();
    }

    public void run() {
        run_transaction(()-> {
            System.out.println("run_transaction");
            return 1;
        });
    }

    private void run_transaction(Transaction transaction) {
        Session session = this.sessionFactory.openSession();
        // TODO: check if session.beginTransaction() required
        session.beginTransaction();
        transaction.run();
        session.getTransaction().commit();
        session.close();
    }

}

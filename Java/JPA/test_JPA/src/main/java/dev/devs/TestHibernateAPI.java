package dev.devs;

// TODO: search why jakarta is imported instead of javax even if I'm using openjdk@11.
// TODO: GPT said sth about this but couldn't be trusted.
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Version;
import org.hibernate.cfg.Configuration;


import java.io.Serializable;
import java.util.Properties;


public class TestHibernateAPI {
    public void printVersion() {
        System.out.println("Hibernate version: " + Version.getVersionString());
    }

    @Entity
    @Table(name="test_Hibernate_1")
    public class Hibernate1 implements Serializable {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        @Column(name = "id")
        private Integer id;
        @Column(name="name")
        private String name;
        // TODO why long? not int?

        public Hibernate1() {}
        /* Getters */

        public String getName() {
            return name;
        }
        public long getId() {
            return id;
        }

        /* Setters */
        public void setName(String name) {
            this.name = name;
        }
        public void setId(Integer id) {
            this.id = id;
        }



    }


    public int testInsertRecord(int id, String name) {
        // Insert record into test_hibernate.test_CRUD_1
        // test_hibernate.test_CRUD_1 schema: id INT, name VARCHAR(20)
        /*
            DBURI = mysql://test_hibernate:1234@localhost/test_hibernate
            create user 'test_hibernate'@'localhost' identified by '1234';
            create [if not exist] database test_hibernate;
            grant all privileges on test_hibernate.* to 'test_hibernate'@'localhost';
            flush privileges;
            create [if not exist] table test_hibernate.test_Hibernate_1 (id INT, name VARCHARΩ(20);
         */
        Hibernate1 hibernate1;
        Properties prop = new Properties();
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/test_hibernate");
        prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        prop.setProperty("hibernate.connection.username", "test_hibernate");
        prop.setProperty("hibernate.connection.password", "1234");
        prop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        prop.setProperty("connection.pool_size", "1");
        prop.setProperty("show_sql", "true");
        // TODO can I set mapping in property or related xml?

        SessionFactory sessionFactory = new Configuration().addProperties(prop).buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        hibernate1 = new Hibernate1();

        hibernate1.setId(id);
        hibernate1.setName(name);

        session.save(hibernate1);
        session.getTransaction().commit();
        session.close();




        return 1;
    }

}

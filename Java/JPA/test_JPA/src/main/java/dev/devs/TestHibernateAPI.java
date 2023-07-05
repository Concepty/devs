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
    /*
     * $ mysql -uroot -p
     * > create user
     *
     * */
    public void printVersion() {
        System.out.println("Hibernate version: " + Version.getVersionString());
    }

    @Entity
    @Table(name="test_CRUD")
    public static class Hibernate1 {
        /*
        * $ mysql -uroot -p
        * > create user 'test_hibernate'@'localhost' identified by '1234';
        * > create database test_hibernate;
        * > create table test_hibernate.test_CRUD (id INT PRIMARY KEY, name VARCHAR(20));
        * > grant all privileges on test_hibernate.* to 'test_hibernate'@'localhost';
        * > flush privileges;
        * */


        // TODO: accidently found an error about auto generating ID in hibernate.
        // To repeat, add          to @Id
        // REF: https://stackoverflow.com/questions/53382161/message-could-not-read-a-hi-value-you-need-to-populate-the-table-hibernate
        @Id
//        @GeneratedValue(strategy=GenerationType.SEQUENCE)
        private Integer id;
        @Column(name="name")
        private String name;
        // TODO why long? not int?

        public Hibernate1() {}
        /* Getters */

        public String getName() {
            return name;
        }
        public Integer getId() {
            return id;
        }

        /* Setters */
        public void setName(String name) {
            this.name = name;
        }
        public void setId(Integer id) { this.id = id; }
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


        // INFO: addAnnotatedClass was the reason of no persister error
        SessionFactory sessionFactory = new Configuration().addProperties(prop).addAnnotatedClass(Hibernate1.class).buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        hibernate1 = new Hibernate1();

        hibernate1.setName(name);
        hibernate1.setId(5);

        session.merge(hibernate1);
        session.getTransaction().commit();
        session.close();




        return 1;
    }

}

/*
* Trouble Shooting
*   Unable to locate persister: dev.devs.TestHibernateAPI$Hibernate1
*   where: save or merge
*   reason: didn't addAnnotatedClass() to hibernate Configuration. Obviously @Entity wasn't enough
*   solution: self-explanatory
*   TODO: check if addAnnotatedClass() is specified in JPA or not.
*
*   'test_hibernate.test_crud_seq' doesn't exist
*   where: save
*   reason: (guess) hibernate needs hibernate_sequence or hibernate_unique_key table
*   reason: (gpt) Hibernate relies on a database sequence to generate the unique identifiers for the entities
*   reason: (gpt, guess) Hibernate need configured table to work properly. Maybe the schema of table should be configured in Hibernate.
*   solution: (not tested) INSERT INTO <schema_name>.hibernate_sequence (next_val) VALUES (0);
*
*
*   No default constructor for entity : dev.devs.TestHibernateAPI$Hibernate1
*   where: merge
*   solution: change Hibernate1 to static to free it from context binding to its enclosing class.
*   reason: maybe TestHibernateAPI$Hibernate1 is not constructable without TestHibernateAPI instance.
*
*
* */

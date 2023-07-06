package dev.devs;

// TODO: search why jakarta is imported instead of javax even if I'm using openjdk@11.
// TODO: GPT said sth about this but couldn't be trusted.
import jakarta.persistence.*;
import org.hibernate.*;
import org.hibernate.Version;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @Column(name="name")
        private String name;
        // TODO why long? not int?

        public Hibernate1() {}
        public Hibernate1(String name) { this.name = name; }
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
        prop.setProperty("hibernate.hbm2ddl.auto", "update");
        prop.setProperty("show_sql", "true");
        // TODO can I set mapping in property or related xml?


        Configuration configuration = new Configuration().addProperties(prop).addAnnotatedClass(Hibernate1.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());




        // INFO: addAnnotatedClass was the reason of no persister error
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Insert methods: persist, save, merge
        Hibernate1 test_merge, test_save, test_persist;

        test_merge = new Hibernate1("merge");
        test_save = new Hibernate1("save");
        test_persist = new Hibernate1("persist");

        session.merge(test_merge);
        session.save(test_save);
        session.persist(test_persist);
        session.getTransaction().commit();

        session.close();
        session = sessionFactory.openSession();
        session.beginTransaction();


        // Read methods: load, get

        // TODO: what is entity name parameter of get
        Hibernate1 test_record = (Hibernate1)session.get(Hibernate1.class, 1);
        System.out.println("id_1:  " + test_record.getName());
        test_record = null;

        // TODO: try to read before commit


        // Update methods update
        test_merge.setName("merge updated");
//        session.update(test_merge);

        // Not allowed twice!
        session.getTransaction().commit();

        // Read again
        test_record = (Hibernate1)session.get(Hibernate1.class, 1);
        System.out.println("id_1:  " + test_record.getName());
        test_record = null;




        // Delete methods delete













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
*   how solved: Cuz Hibernate didn't find persister (dev.devs.TestHibernateAPI$Hibernate1), there might be problem setting or finding.
*               First action was to check valid tutorial to follow because I'm new to this.
*               And noticed that I missed addAnnotatedClass.
*   TODO: check if addAnnotatedClass() is specified in JPA or not.
*
*   'test_hibernate.test_crud_seq' doesn't exist
*   where: save
*   reason: (guess) hibernate needs hibernate_sequence or hibernate_unique_key table
*   reason: (gpt) Hibernate relies on a database sequence to generate the unique identifiers for the entities
*   reason: (gpt, guess) Hibernate need configured table to work properly. Maybe the schema of table should be configured in Hibernate.
*   solution: (not tested) INSERT INTO <schema_name>.hibernate_sequence (next_val) VALUES (0);
*   How solved: test_hibernate.test_crud_seq table come from nowhere. I suspected that my database setting is not proper for hibernate.
*               Of course there are proper way to configure my database. But I rather let hibernate configure itself. So I decided to
*               try create table with Hibernate.
*
*
*   No default constructor for entity : dev.devs.TestHibernateAPI$Hibernate1
*   where: merge
*   solution: change Hibernate1 to static to free it from context binding to its enclosing class.
*   reason: maybe TestHibernateAPI$Hibernate1 is not constructable without TestHibernateAPI instance.
*   how solved: eas-y
*
*   The given object has a null identifier: dev.devs.TestHibernateAPI$Hibernate1
*   where: update
*   solution: ?
*   reason: ?
*   how solved: search what is identifier in @Entity object
*
* */

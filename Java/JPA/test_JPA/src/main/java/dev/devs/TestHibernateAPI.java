package dev.devs;

// TODO: search why jakarta is imported instead of javax even if I'm using openjdk@11.
// TODO: GPT said sth about this but couldn't be trusted.
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Version;


import java.util.Properties;


public class TestHibernateAPI {
    public void printVersion() {
        System.out.println("Hibernate version: " + Version.getVersionString());
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
            create [if not exist] table test_hibernate.test_JPA_1 (id INT, name VARCHARΩ(20);
         */
        // TODO: check if it's a right practice to instantiate Property this way.
        Properties prop = new Properties();
        // TODO: how to read com.mysql.cj.jdbc.Driver?
        // TODO: should key of prop be FQN?
        prop.setProperty("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
        // TODO: isn't there any system defined port for mysql? is 3306 needed to be configured?
        prop.setProperty("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/test_hibernate");
        prop.setProperty("javax.persistence.jdbc.user", "test_hibernate");
        prop.setProperty("javax.persistence.jdbc.password", "1234");

        // TODO: It's weird that createEntityManagerFactory is static , where is persistenceUnitName used?
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test_hibernate", prop);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // TODO: why? what does it do?
        entityManager.getTransaction().begin();
        


        return 1;
    }

}

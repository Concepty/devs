package dev.devs;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.hibernate.cfg.Configuration;

import java.util.List;


public class TestHibernate {
    /*
     * $ mysql -uroot -p
     * > create user 'test_hibernate'@'localhost' identified by '1234';
     * > create database test_hibernate;
     * > grant all privileges on test_hibernate.* to 'test_hibernate'@'localhost';
     * > flush privileges;
     * */
    private SessionFactory sessionFactory = null;

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
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(TestEntity.class);

        // TODO: is this really best?
        // TODO: builder needs conf, and conf needs builder to build?
        StandardServiceRegistryBuilder SSRBuilder =
                new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
        StandardServiceRegistry SSR = SSRBuilder.build();

        try {
            this.sessionFactory = configuration.buildSessionFactory(SSR);
        } catch (HibernateError he) {
            // TODO: Should I destroy SSR on exception?
            StandardServiceRegistryBuilder.destroy(SSR);
            throw he;
        }
    }
    private interface Transaction {
        public void run(EntityManager em);
    }

    public void insert_transaction(String[] strs) {
        // Suprisingly, JPA doesn't support insert operation in JPA Criteria
        // API.
        Session session = this.sessionFactory.openSession();
        // TODO: check if session.beginTransaction() required
        session.beginTransaction();

        for (String str: strs) {
            TestEntity te = new TestEntity(str);
            // TODO: is there any overhead calling persist for each object?
            // TODO: if I don't have to, can I insert in bulk?
            session.persist(te);
        }
        session.getTransaction().commit();
        session.close();
    }

    public void select_transaction() {
        run_transaction((em) -> {

            CriteriaQuery<TestEntity> criteriaQuery = em.getCriteriaBuilder().createQuery(TestEntity.class);
            // TODO: what is root? why is root used?
            Root<TestEntity> root = criteriaQuery.from(TestEntity.class);
            criteriaQuery.select(root);
            // TODO: one tutorial suggested to use session to creat query.
            // TODO: need to find good practice.
            List<TestEntity> results = em.createQuery(criteriaQuery).getResultList();

            for (TestEntity record: results) {
                System.out.println("record content: " + record.getContent());
            }
        });
    }
    public void update_transaction() {
        run_transaction((em) -> {


            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<TestEntity> cq = cb.createQuery(TestEntity.class);
            // TODO: can I share root for criteria query and criteria update?
            Root<TestEntity> cq_root = cq.from(TestEntity.class);
            cq.select(cq_root);
            List<TestEntity> results = em.createQuery(cq).getResultList();

            CriteriaUpdate<TestEntity> cu = cb.createCriteriaUpdate(TestEntity.class);
            Root<TestEntity> cu_root = cu.from(TestEntity.class);
            cu.set(cu_root.get("content"), "updated");


            // TODO: allow_update_outside_transaction is right setting to be true?
            int updatedCount = em.createQuery(cu).executeUpdate();
            System.out.println(updatedCount);
        });
    }

    private void run_transaction(Transaction transaction) {
        Session session = this.sessionFactory.openSession();
        // TODO: check if session.beginTransaction() required
        session.beginTransaction();
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        EntityTransaction em_trans = em.getTransaction();
        em_trans.begin();


        transaction.run(em);
        // TODO: weird thing is, that I need to commit in entityTransaction not session to udpate
        // TODO: insert is committed with session.
        em_trans.commit();

        session.close();
    }

}

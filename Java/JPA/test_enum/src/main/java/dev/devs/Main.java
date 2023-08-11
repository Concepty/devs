package dev.devs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

import dev.devs.TestConverterEnum.ConvertedEnum;
/**
 * https://www.baeldung.com/jpa-persisting-enums-in-jpa
 */

public class Main {

    private static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("IMDb");

    /**
     * EntityManager em = factory.createEntityManager();
     *         em.getTransaction().begin();
     *         transaction.run(em);
     *         em.getTransaction().commit();
     *         em.close();
     */


    public static void main(String[] args) {
//        insertMyEnum();
        insertMyValuedEnum();
        // insertMyConvertedEnum();
    }

    public static void insertMyEnum() {
        TestEnum t1 = new TestEnum(TestEnum.MyEnum.ENUM1, TestEnum.MyEnum.ENUM1);
        TestEnum t2 = new TestEnum(TestEnum.MyEnum.ENUM2, TestEnum.MyEnum.ENUM2);
        TestEnum t3 = new TestEnum(TestEnum.MyEnum.ENUM3, TestEnum.MyEnum.ENUM3);

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(t1);
        em.persist(t2);
        em.persist(t3);
        em.getTransaction().commit();
        em.close();
    }

    public static void insertMyValuedEnum() {

        TestValuedEnum t1 = new TestValuedEnum(TestValuedEnum.MyValuedEnum.ENUM1);
        TestValuedEnum t2 = new TestValuedEnum(TestValuedEnum.MyValuedEnum.ENUM2);
        TestValuedEnum t3 = new TestValuedEnum(TestValuedEnum.MyValuedEnum.ENUM2);

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(t1);
        em.persist(t2);
        em.persist(t3);
        em.getTransaction().commit();
        em.close();

        System.out.println("t1, t2, t3 committed");

        em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TestValuedEnum> cq = cb.createQuery(TestValuedEnum.class);

        Root<TestValuedEnum> root = cq.from(TestValuedEnum.class);

        cq.select(root);

//        Predicate predicate = cb.equal(root.get("id"), 1);
//        cq.where(predicate);

        List<TestValuedEnum> resultList = em.createQuery(cq).getResultList();

        System.out.println("selected records");
        for (TestValuedEnum t: resultList) {
            System.out.println(t.getE());
        }
    }

    public static void insertMyConvertedEnum() {
        TestConverterEnum c1 = new TestConverterEnum(TestConverterEnum.ConvertedEnum.ENUM1);
        TestConverterEnum c2 = new TestConverterEnum(TestConverterEnum.ConvertedEnum.ENUM2);
        TestConverterEnum c3 = new TestConverterEnum(TestConverterEnum.ConvertedEnum.ENUM3);

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.getTransaction().commit();
        em.close();


    }

}
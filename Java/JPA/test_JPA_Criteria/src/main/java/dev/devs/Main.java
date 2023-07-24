package dev.devs;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.*;
import dev.devs.Tables.*;
import jakarta.persistence.criteria.*;
import org.hibernate.sql.results.internal.domain.CircularFetchImpl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Testing JPA Criteria
 * Additionally, Complex Schema.
 * This project doesn't include fine-tuning data type.
 * Doing complex Schema. I need to learn how to make one-to-many and many-to-many relationship
 * in JPA. -> @OneToany
 *
 */

public class Main {
    // TODO: handle hibernate errro
    // error(Hibernate: alter table musics drop foreign key FKbysp2ixjy266hujxff4g7ic7a)
    // It happens when there is no tables in database. because Hibernate try to drop foreign keys first
    // before dropping tables.
    private static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("test_Criteria");

    public static void main(String[] args) {

        System.out.println("Insert: 뉴 진스 앨범! 뉴 진스!");
        runInsert((em) ->{
            Album newJeans = new Album("New Jeans");
            Music cookie = new Music("Cookie", "NewJeans", new Time(236000), newJeans);
            Music hypeBoy = new Music("Hype Boy", "NewJeans", new Time(180000), newJeans);
            Music attention = new Music("Attention", "NewJeans", new Time(181000), newJeans);
//            List<Music> musics = new ArrayList<>();
//            musics.add(cookie);
//            musics.add(hypeBoy);
//            musics.add(attention);
//            newJeans.setMusics(musics);

            em.persist(newJeans);
            em.persist(cookie);
            em.persist(hypeBoy);
            em.persist(attention);
        });

        System.out.println("Insert: 뉴 진스 앨범! Get Up!");
        runInsert((em) ->{
            Album getUp = new Album("Get Up");
            Music newJeans = new Music("New Jeans", "NewJeans", new Time(109000), getUp);
            Music superShy = new Music("Super Shy", "NewJeans", new Time(155000), getUp);
            em.persist(newJeans);
            em.persist(superShy);
            em.persist(getUp);
        });

        if (false) {
            System.out.println("Delete: Get Up 앨범이 지워집니다 ㅠㅠ");
            runDelete((em) -> {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Album> cq = cb.createQuery(Album.class);
                Root<Album> album = cq.from(Album.class);
                cq.where(cb.equal(album.get("name"), "Get Up"));
                TypedQuery<Album> query = em.createQuery(cq);
                List<Album> results = query.getResultList();

                if (results.size() == 1) em.remove(results.get(0));
                else throw new RuntimeException("Failed to remove Get Up Album");
            });

            System.out.println("Transaction: Get Up의 노래 들은 어떻게 되었을까요?");
            runQuery((em) -> {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Music> cq = cb.createQuery(Music.class);
                Root<Music> music = cq.from(Music.class);
                Predicate condition = cb.or(cb.equal(music.get("name"), "New Jeans"), cb.equal(music.get("name"), "Super Shy"));
                cq.where(condition);
                List<Music> result = em.createQuery(cq).getResultList();

                if (result == null) throw new RuntimeException("query failed");
                if (result.size() == 0) System.out.println("Get Up의 노래는 다 지워졌어요 ㅠㅠ");
                else if (result.size() == 2) System.out.println("노래는 무사하네요!");

            });
        }
        if (false) {
            System.out.println("Cookie가 지워집니다 ㅠㅠ.");
            runDelete((em) -> {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Music> cq = cb.createQuery(Music.class);
                Root<Music> musics = cq.from(Music.class);
                Predicate condition = cb.equal(musics.get("name"), "Cookie");
                cq.where(condition);
                List<Music> result = em.createQuery(cq).getResultList();

                em.remove(result.get(0));
            });
        }

        if (true) {
//            System.out.println("Cookie와 같은 앨범에는 어떤 노래들이 있을까요? (Query 2회)");
//            runQuery((em) -> {
//                /**
//                 * CriteriaBuilder
//                 * CriteriaQuery
//                 * Root
//                 * Predicate
//                 * Expression
//                 */
//                CriteriaBuilder cb = em.getCriteriaBuilder();
//                CriteriaQuery cq = cb.createQuery();
//                Root<Music> musicRoot = cq.from(Music.class);
//                cq.where(cb.equal(musicRoot.get("name"), "Cookie"));
//
//                List<Music> cookieList = em.createQuery(cq).getResultList();
//
//                Album cookieAlbum = cookieList.get(0).getAlbum();
//
//                CriteriaQuery cq2 = cb.createQuery();
//                Root<Music> musicRoot2 = cq2.from(Music.class);
//                cq2.where(cb.equal(musicRoot2.get("album"), cookieAlbum));
//
//                List<Music> results = em.createQuery(cq2).getResultList();
//
//                for (Music r: results) {
//                    System.out.println("이런 노래들이 있어요. " + r.getName());
//                }
//
            System.out.println("Cookie와 같은 앨범에는 어떤 노래들이 있을까요? (subquery)");
            // subquery
            // select * from Music where album_id = (
            // select id from Album where id = (
            // select album_id from Music where name = "Cookie"
            // ))
            runQuery(em -> {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Music> cq = cb.createQuery(Music.class);
                Root<Music> musics = cq.from(Music.class);

                Subquery<Album> subquery = cq.subquery(Album.class);
                Root<Album> albumIdRoot = subquery.from(Album.class);

                Subquery<Integer> nestedSubquery = subquery.subquery(Integer.class);
                Root<Music> musicRoot = nestedSubquery.from(Music.class);

                nestedSubquery.select(musicRoot.get("album")).where(cb.equal(musicRoot.get("name"), "Cookie"));
                subquery.select(albumIdRoot.get("id")).where(cb.equal(albumIdRoot.get("id"), nestedSubquery));
                cq.select(musics).where(cb.equal(musics.get("album"), subquery));

                List<Music> results = em.createQuery(cq).getResultList();

                for (Music m: results) {
                    System.out.println("이런 노래들이 있어요. " + m.getName());
                }



            });



//                CriteriaBuilder cb = em.getCriteriaBuilder();
//                CriteriaQuery<Music> cq = cb.createQuery(Music.class);
//                Root<Music> musics =  cq.from(Music.class);
//
//                Subquery<Album> subquery = cq.subquery(Album.class);
//                Root<Album> albums = subquery.from(Album.class);
//                subquery.select(albums.get("id")).where(cb.equal(albums.get("name"), "New Jeans"));
//
//                // NOTE: musics.get() gets member of entity class, not column
//                cq.select(musics).where(cb.equal(musics.get("album"), subquery));
//
//                List<Music> results = em.createQuery(cq).getResultList();
//
//                for (Music r: results) {
//                    System.out.println("이런 노래들이 있어요. " + r.getName());
//                }
//            });

        }

    }

    public static void runQuery(Transaction transaction) {
        EntityManager em = factory.createEntityManager();
        transaction.run(em);
        em.close();
    }
    public static void runInsert(Transaction transaction) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        transaction.run(em);
        em.getTransaction().commit();
        em.close();
    }
    public static void runDelete(Transaction transaction) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        transaction.run(em);
        em.getTransaction().commit();
        em.close();
    }

    private interface Transaction {
        public void run(EntityManager em);
    }



}
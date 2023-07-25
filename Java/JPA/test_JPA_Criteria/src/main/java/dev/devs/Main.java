package dev.devs;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.*;
import dev.devs.Tables.*;
import jakarta.persistence.criteria.*;
import org.hibernate.sql.results.internal.domain.CircularFetchImpl;

import javax.swing.*;
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
            Music cookie = new Music("Cookie", "NewJeans", 236, newJeans);
            Music hypeBoy = new Music("Hype Boy", "NewJeans", 180, newJeans);
            Music attention = new Music("Attention", "NewJeans", 181, newJeans);
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
            Music newJeans = new Music("New Jeans", "NewJeans", 109, getUp);
            Music superShy = new Music("Super Shy", "NewJeans", 155, getUp);
            em.persist(newJeans);
            em.persist(superShy);
            em.persist(getUp);
        });


        System.out.println("Insert:  호랑수월가!");
        runInsert(em -> {
            Album horangAlbum = new Album("호랑수월가");
            Music horangMusic = new Music("호랑수월가", "함유주", 288, horangAlbum);
            em.persist(horangAlbum);
            em.persist(horangMusic);
        });

        System.out.println("Insert: First Dream");
        runInsert(em -> {
            Music spark = new Music("Spark", "XYNSIA", 186);
            Music parallel = new Music("Parallel", "XYNSIA", 182);
            Music phantomRain = new Music("환상비", "XYNSIA", 265);
            Music glassCandy = new Music("유리맛 사탕", "XYNSIA", 226);
            Music lucidDream = new Music("Lucid Dream", "XYNSIA", 212);

            List<Music> musics = Arrays.asList(spark, parallel, phantomRain, glassCandy,lucidDream);
            for (Music m: musics) {
                // Testing if late persisting of album can be reflected on DB
                // I doubt it, but it worked.
                // TODO: find out why late changes persisted
                /**
                 * setting Album
                 * setting Album
                 * setting Album
                 * setting Album
                 * setting Album //First, why is setting Album early than inserting it even if I persisted it early.
                 * Hibernate: insert into musics (album_id,artist,name,play_time,rating,id) values (?,?,?,?,?,?)
                 * Hibernate: insert into musics (album_id,artist,name,play_time,rating,id) values (?,?,?,?,?,?)
                 * Hibernate: insert into musics (album_id,artist,name,play_time,rating,id) values (?,?,?,?,?,?)
                 * Hibernate: insert into musics (album_id,artist,name,play_time,rating,id) values (?,?,?,?,?,?)
                 * Hibernate: insert into musics (album_id,artist,name,play_time,rating,id) values (?,?,?,?,?,?)
                 * Hibernate: insert into albums (name,id) values (?,?)
                 * //Second, is updating cascaded from Album?
                 * Hibernate: update musics set album_id=?,artist=?,name=?,play_time=?,rating=? where id=?
                 * Hibernate: update musics set album_id=?,artist=?,name=?,play_time=?,rating=? where id=?
                 * Hibernate: update musics set album_id=?,artist=?,name=?,play_time=?,rating=? where id=?
                 * Hibernate: update musics set album_id=?,artist=?,name=?,play_time=?,rating=? where id=?
                 * Hibernate: update musics set album_id=?,artist=?,name=?,play_time=?,rating=? where id=?
                 */


                em.persist(m);
            }

            Album firstDream = new Album("First Dream", musics);
            em.persist(firstDream);

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

        if (false) {
            System.out.println("Cookie와 같은 앨범에는 어떤 노래들이 있을까요? (subquery)");
            /**
             * subquery
             * select * from Music where album_id = (
             * select id from Album where id = (
             * select album_id from Music where name = "Cookie"))
             */
            // TODO: there is unnecessary subquery return id from id.
            // remove the subquery and make valid example with more table

//            runQuery(em -> {
//                CriteriaBuilder cb = em.getCriteriaBuilder();
//                CriteriaQuery<Music> cq = cb.createQuery(Music.class);
//                Root<Music> musics = cq.from(Music.class);
//
//                Subquery<Album> subquery = cq.subquery(Album.class);
//                Root<Album> albumIdRoot = subquery.from(Album.class);
//
//                Subquery<Integer> nestedSubquery = subquery.subquery(Integer.class);
//                Root<Music> musicRoot = nestedSubquery.from(Music.class);
//
//                nestedSubquery.select(musicRoot.get("album")).where(cb.equal(musicRoot.get("name"), "Cookie"));
//                subquery.select(albumIdRoot.get("id")).where(cb.equal(albumIdRoot.get("id"), nestedSubquery));
//                cq.select(musics).where(cb.equal(musics.get("album"), subquery));
//
//                List<Music> results = em.createQuery(cq).getResultList();
//
//                for (Music m: results) {
//                    System.out.println("이런 노래들이 있어요. " + m.getName());
//                }
//            });


        }
        if (true) {

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
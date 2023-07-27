package dev.devs;

import java.util.Arrays;
import java.util.List;
import dev.devs.Tables.*;

public class TestSet {
    public static void insertDataSet() {

        JPAFunctions.runInsert(em -> {
            Album newJeans = new Album("New Jeans");
            Music cookie = new Music("Cookie", "NewJeans", 236, newJeans);
            Music hypeBoy = new Music("Hype Boy", "NewJeans", 180, newJeans);
            Music attention = new Music("Attention", "NewJeans", 181, newJeans);
            em.persist(newJeans);
            em.persist(cookie);
            em.persist(hypeBoy);
            em.persist(attention);
        });

        JPAFunctions.runInsert((em) ->{
            Tables.Album getUp = new Album("Get Up");
            Music newJeans = new Music("New Jeans", "NewJeans", 109, getUp);
            Music superShy = new Music("Super Shy", "NewJeans", 155, getUp);
            em.persist(newJeans);
            em.persist(superShy);
            em.persist(getUp);
        });

        JPAFunctions.runInsert(em -> {
            Tables.Album horangAlbum = new Album("호랑수월가");
            Music horangMusic = new Music("호랑수월가", "함유주", 288, horangAlbum);
            em.persist(horangAlbum);
            em.persist(horangMusic);
        });

        JPAFunctions.runInsert(em -> {
            Music spark = new Music("Spark", "XYNSIA", 186);
            Music parallel = new Music("Parallel", "XYNSIA", 182);
            Music phantomRain = new Music("환상비", "XYNSIA", 265);
            Music glassCandy = new Music("유리맛 사탕", "XYNSIA", 226);
            Music lucidDream = new Music("Lucid Dream", "XYNSIA", 212);

            List<Music> musics = Arrays.asList(spark, parallel, phantomRain, glassCandy,lucidDream);
            for (Music m: musics) {
                em.persist(m);
            }
            Tables.Album firstDream = new Tables.Album("First Dream", musics);
            em.persist(firstDream);

        });
    }
}

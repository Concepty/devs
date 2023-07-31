package dev.devs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import dev.devs.Tables.*;
import jakarta.persistence.EntityManager;

public class TestSet {

    /**
     * Artist
     * Album
     * Music
     * User
     * Playlist
     * User Music Rating
     * User ...
     */


    public static void insertTestSet1() {
        final int ARTISTS = 100;
        final int ALBUMS = 100;


        //ARTIST
        List<Artist> artists = new ArrayList<>();
        JPAFunctions.runInsert(em -> {
            int artistCount = 0;

            for (artistCount = 0; artistCount < ARTISTS; artistCount++){
                Artist artist = new Artist("Artist" + Integer.toString(artistCount));
                artists.add(artist);
                em.persist(artist);
            }
        });

        //ALBUM
        List<Album> albums = new ArrayList<>();
        JPAFunctions.runInsert(em -> {
            int albumCount = 0;

            final int ARTISTS_IN_ALBUM = 3;


            for (albumCount = 0; albumCount < ALBUMS; albumCount++) {
                List<Artist> tempArtist = new ArrayList<>();
                ThreadLocalRandom random = ThreadLocalRandom.current();
                random.ints(0, ARTISTS)
                        .distinct()
                        .limit(ARTISTS_IN_ALBUM)
                        .forEach(i -> {
                            tempArtist.add(artists.get(i));
                        });
                Album album = new Album("Album" + Integer.toString(albumCount), tempArtist);
                tempArtist.forEach(artist -> {
                    artist.addAlbum(album);
                });
                albums.add(album);
                em.persist(album);
            }
        });

    }


//    public static void insertDataSet() {
//
//        JPAFunctions.runInsert(em -> {
//            Album newJeans = new Album("New Jeans");
//            Music cookie = new Music("Cookie", "NewJeans", 236, newJeans);
//            Music hypeBoy = new Music("Hype Boy", "NewJeans", 180, newJeans);
//            Music attention = new Music("Attention", "NewJeans", 181, newJeans);
//            em.persist(newJeans);
//            em.persist(cookie);
//            em.persist(hypeBoy);
//            em.persist(attention);
//        });
//
//        JPAFunctions.runInsert((em) ->{
//            Tables.Album getUp = new Album("Get Up");
//            Music newJeans = new Music("New Jeans", "NewJeans", 109, getUp);
//            Music superShy = new Music("Super Shy", "NewJeans", 155, getUp);
//            em.persist(newJeans);
//            em.persist(superShy);
//            em.persist(getUp);
//        });
//
//        JPAFunctions.runInsert(em -> {
//            Tables.Album horangAlbum = new Album("호랑수월가");
//            Music horangMusic = new Music("호랑수월가", "함유주", 288, horangAlbum);
//            em.persist(horangAlbum);
//            em.persist(horangMusic);
//        });
//
//        JPAFunctions.runInsert(em -> {
//            Music spark = new Music("Spark", "XYNSIA", 186);
//            Music parallel = new Music("Parallel", "XYNSIA", 182);
//            Music phantomRain = new Music("환상비", "XYNSIA", 265);
//            Music glassCandy = new Music("유리맛 사탕", "XYNSIA", 226);
//            Music lucidDream = new Music("Lucid Dream", "XYNSIA", 212);
//
//            List<Music> musics = Arrays.asList(spark, parallel, phantomRain, glassCandy,lucidDream);
//            for (Music m: musics) {
//                em.persist(m);
//            }
//            Tables.Album firstDream = new Tables.Album("First Dream", musics);
//            em.persist(firstDream);
//
//        });
//    }
}

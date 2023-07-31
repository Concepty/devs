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


    /**
     * Case 1: create artists, persist artists, create albums, persist albums
     * Result 1: JoinTable is empty
     *
     * Case 2: create artists, create albums, persist both (either case)
     * Result 2: Join Table is present
     *
     * Case 3: create albums, persist albums, create artists, persist artists
     * Result 3: Join Table is present! Compare it with case1
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
//                em.persist(artist);
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
            }
        });

        JPAFunctions.runInsert(em -> {
            artists.forEach(artist -> {
                em.persist(artist);
            });

            albums.forEach(album -> {
                em.persist(album);
            });

        });
    }

    public static void insertTestSet2() {
        final int ALBUMS = 100;
        final int ARTISTS = 100;

        final int ALBUMS_IN_ARTISTS = 5;

        //ALBUM
        List<Album> albums = new ArrayList<>();
        JPAFunctions.runInsert(em -> {
            int albumCount = 0;
            for (albumCount = 0; albumCount < ALBUMS; albumCount++) {
                Album album = new Album("Album" + Integer.toString(albumCount));
                albums.add(album);
                em.persist(album);
            }
        });

        //Artists
        List<Artist> artists = new ArrayList<>();
        JPAFunctions.runInsert(em -> {
            int artistCount = 0;
            for (artistCount = 0; artistCount < ARTISTS; artistCount++) {
                List<Album> tempAlbums = new ArrayList<>();
                ThreadLocalRandom random = ThreadLocalRandom.current();
                random.ints(0, ALBUMS)
                        .distinct()
                        .limit(ALBUMS_IN_ARTISTS)
                        .forEach(i -> {
                            tempAlbums.add(albums.get(i));
                        });
                Artist artist = new Artist("Artists" + Integer.toString(artistCount));
                tempAlbums.forEach(album -> {
                    artist.addAlbum(album);
                });
                artists.add(artist);
                em.persist(artist);
            }
        });
    }
}

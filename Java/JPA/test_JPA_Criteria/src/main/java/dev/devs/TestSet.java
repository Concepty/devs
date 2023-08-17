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
     * Testing Directionality in JPA mapping
     * 
     * Case1: artists created and persisted without even knowing albums.
     * each album in albums knows its artist but persisting it doesn't create
     * JoinTable.
     * 
     * Q. Does each album knowing artists create artist_album table?
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
                Album album = new Album("Album" + Integer.toString(albumCount));
                tempArtist.forEach(artist -> {
                    album.addArtist(artist);
                    // artist.addAlbum(album); // changing artist object after persisting
                });
                albums.add(album);
                em.persist(album);
            }
        });
    }

    /**
     * Testing Directionality in JPA mapping
     * 
     * Case 2: create artists and albums. each element of the lists knows each other
     * order of persist() doesn't matter.
     * 
     * Q. Does each artist and album knowing each other create JoinTable?
     */
    public static void insertTestSet2(boolean artist_first) {
        final int ALBUMS = 100;
        final int ARTISTS = 100;

        //ARTIST
        final int ALBUMS_IN_ARTISTS = 5;

        List<Artist> artists = new ArrayList<>();
        for (int artistCount = 0; artistCount < ARTISTS; artistCount++) {
            Artist artist = new Artist("Artist" + Integer.toString(artistCount));
            artists.add(artist);
        }

        //ALBUM
        List<Album> albums = new ArrayList<>();
        for (int albumCount = 0; albumCount < ALBUMS; albumCount++) {
            List<Artist> tempArtists = new ArrayList<>();
            ThreadLocalRandom random = ThreadLocalRandom.current();
            random.ints(0, ARTISTS)
                    .distinct()
                    .limit(ALBUMS_IN_ARTISTS)
                    .forEach(i -> {
                        tempArtists.add(artists.get(i));
                    });
            Album album = new Album("Album" + Integer.toString(albumCount));
            tempArtists.forEach(artist -> {
                album.addArtist(artist);
                artist.addAlbum(album);
            });
            albums.add(album);
        }

        JPAFunctions.runInsert(em -> {
           if (artist_first) {
                artists.forEach(artist -> {
                    em.persist(artist);
                });
                albums.forEach(album -> {
                    em.persist(album);
                });
            } else {
                albums.forEach(album -> {
                    em.persist(album);
                });
                artists.forEach(artist -> {
                    em.persist(artist);
                });
            }
        });
    }

    /**
     * Testing Directionality in JPA mapping
     * 
     * Case 3: create and persist albums first. Each album in albums doesn't know its
     * artists. Then create artists, each artist knows its albums and since albums are
     * persisted already, whether each album knows artists doesn't matter.
     * 
     * Compare with Case1
     * 
     * Q. Does each artist knowing its albums create JoinTable? Yes!
     */
    public static void insertTestSet3(boolean doesAlbumKnowArtists) {
        final int ALBUMS = 100;
        final int ARTISTS = 100;q

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
                    if (doesAlbumKnowArtists) album.addArtist(artist);
                });
                artists.add(artist);
                em.persist(artist);
            }
        });
    }
}

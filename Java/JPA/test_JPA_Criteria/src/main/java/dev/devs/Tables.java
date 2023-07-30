package dev.devs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.Instant;
import java.util.List;

public class Tables {
    @Entity
    @Table(name = "Music")
    public static class Music {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "name")
        @Getter @Setter
        private String name;

        @Column(name = "playtime")
        @Getter @Setter
        private int playtime;

        @Column(name = "rating")
        @Getter @Setter
        // average rating
        private double rating;

        @Column(name = "rating_count")
        @Getter @Setter
        // number of rating given by users
        private long ratingCount;

        @ManyToOne
        @JoinColumn(name = "album_id")
        private Album album;

        @ManyToMany(mappedBy = "musics")
        private List<Artist> artists;

        @ManyToMany(mappedBy = "musics")
        private List<Playlist> playlists;

        public Music() {}
    }

    @Entity
    @Table(name = "Album")
    public static class Album {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "name")
        @Getter @Setter
        private String name;

        @Column(name = "rating")
        @Getter @Setter
        private double rating;

        @Column(name = "rating_count")
        @Getter @Setter
        private long ratingCount;

        @OneToMany(mappedBy = "album")
        private List<Music> musics;

        @ManyToMany(mappedBy = "artists")
        private List<Artist> artists;

        public Album () {}
    }

    @Entity
    @Table(name = "Artist")
    public static class Artist {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "name")
        @Getter @Setter
        private String name;

        @ManyToMany
        @JoinTable(
                name = "Artist_Music",
                joinColumns = @JoinColumn(name = "artist_id"),
                inverseJoinColumns = @JoinColumn(name = "music_id")
        )
        private List<Music> musics;

        @ManyToMany
        @JoinTable(
                name = "Artist_Album",
                joinColumns = @JoinColumn(name = "artist_id"),
                inverseJoinColumns = @JoinColumn(name = "album_id")
        )
        private List<Album> albums;


        public Artist() {}
    }

    @Entity
    @Table(name = "User")
    public static class User {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "name")
        @Getter @Setter
        private String name;

        @OneToMany(mappedBy = "user")
        private List<MusicRating> musicRating;

        @OneToMany(mappedBy = "user")
        private List<AlbumRating> albumRating;

        public User() {}
    }

    @Entity
    @Table(name = "Playlist")
    public static class Playlist {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @ManyToMany
        @JoinTable(
                name = "Playlist_Music",
                joinColumns = @JoinColumn(name = "playlist_id"),
                inverseJoinColumns = @JoinColumn(name = "music_id")
        )
        private List<Music> musics;

        public Playlist () {}
    }

    @Entity
    @Table(name = "Music_Rating")
    public static class MusicRating {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "rating")
        private int rating;

        @Column(name = "timestamp")
        private Instant timestamp;

        @ManyToOne
        @JoinColumn(name = "music_id")
        private Music music;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        public MusicRating() {}

    }

//    @Entity
//    @Table(name = "Music_Comment")
//    public static class MusicComment {
//
//        @Id
//        @GeneratedValue(strategy = GenerationType.AUTO)
//        private long id;
//
//        @Column(name = "content")
//        private String content;
//
//        @Column(name = "timestamp")
//        private Instant timestamp;
//
//        @Column(name = "thumb_up")
//        private long thumbUp;
//
//        @Column(name = "thumb_down")
//        private long thumbDown;
//
//
//        public MusicComment() {}
//    }

    @Entity
    @Table(name = "Album_Rating")
    public static class AlbumRating {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "rating")
        private int rating;

        @Column(name = "timestamp")
        private Instant timestamp;

        @ManyToOne
        @JoinColumn(name = "album_id")
        private Album album;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        public AlbumRating() {}
    }


//    @Entity
//    @Table(name = "Album_Comment")
//    public static class AlbumComment {
//
//        @Id
//        @GeneratedValue(strategy = GenerationType.AUTO)
//        private long id;
//
//        @Column(name = "content")
//        private String content;
//
//        @Column(name = "timestamp")
//        private Instant timestamp;
//
//        @Column(name = "thumb_up")
//        private long thumbUp;
//
//        @Column(name = "thumb_down")
//        private long thumbDown;
//
//        public AlbumComment() {}
//    }

//    @Entity
//    @Table(name = "Artist_Comment")
//    public static class ArtistComment {
//        @Id
//        @GeneratedValue(strategy = GenerationType.AUTO)
//        private long id;
//
//        @Column(name = "content")
//        private String content;
//
//        @Column(name = "timestamp")
//        private Instant timestamp;
//
//        @Column(name = "thumb_up")
//        private long thumbUp;
//
//        @Column(name = "thumb_down")
//        private long thumbDown;
//
//        public ArtistComment() {}
//    }

}

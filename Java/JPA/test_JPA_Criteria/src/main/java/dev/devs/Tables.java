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

        @ManyToOne
        @JoinColumn(name = "album_id")
        private Album album;

        @Column(name = "rating")
        @Getter @Setter
        private double rating;

        @Column(name = "rating_count")
        @Getter @Setter
        private long ratingCount;

        @ManyToMany(mappedBy = "musicRating")
        private List<User> userRatings;

        @ManyToMany
        @JoinTable(
                name = "Music_Artist",
                joinColumns = @JoinColumn(name = "music_id"),
                inverseJoinColumns = @JoinColumn(name = "artist_id")
        )
        private List<Artist> artists;

        @OneToMany(mappedBy = "music")
        private List<UserMusicRating> ratings;

        @OneToMany(mappedBy = "music")
        private List<MusicComment> comments;


        public Music() {}
    }

    @Entity
    @Table(name = "Music_User_Rating")
    public static class MusicUserRating {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;



        public MusicUserRating() {}
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

        @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
        private List<Music> musics;

        @ManyToMany
        @JoinTable(
                name = "Album_Artist",
                joinColumns = @JoinColumn(name = "album_id"),
                inverseJoinColumns = @JoinColumn(name = "artist_id")
        )
        private List<Artist> artists;


        public Album () {}
    }

    @Entity
    @Table(name = "Artist")
    public static class Artist {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @ManyToMany(mappedBy = "artists")
        private List<Music> musics;

        @ManyToMany(mappedBy = "artists")
        private List<Album> albums;


        public Artist() {}
    }

    @Entity
    @Table(name = "User")
    public static class User {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @OneToMany(mappedBy = "user")
        private List<UserMusicRating> musicRating;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private List<MusicComment> musicComments;


        public User() {}
    }

    @Entity
    @Table(name = "Playlist")
    public static class Playlist {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        public Playlist () {}
    }

    @Entity
    @Table(name = "User_Music_Rating")
    public static class UserMusicRating {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "rating")
        private int rating;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "music_id")
        private Music music;

    }

    @Entity
    @Table(name = "Comment")
    public static class MusicComment {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "content")
        private String content;

        @Column(name = "timestamp")
        private Instant timestamp;

        @Column(name = "thumb_up")
        private int thumbUp;

        @Column(name = "thumb_down")
        private int thumbDown;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "music_id")
        private Music music;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "user_id")
        private User user;

        public MusicComment() {}
    }

}

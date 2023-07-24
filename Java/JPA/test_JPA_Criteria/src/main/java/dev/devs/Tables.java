package dev.devs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.List;

public class Tables {
    @Entity
    @Table(name="musics")
    public static class Music {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Getter
        private int id;

        //NOTE: nullable is true by default
        @Column(name="name", nullable = false)
        @Getter @Setter
        private String name;

        @Column(name="artist")
        @Getter @Setter
        private String artist;

        @Column(name="play_time")
        @Getter @Setter
        private Time playTime;

        @Column(name="rating", columnDefinition = "FLOAT DEFAULT 0.0")
        @Getter @Setter
        private float rating;

        // Question: Check if this member persisted. It should be as I understood. -> album_id column
        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name="album_id")
        @Getter @Setter
        private Album album;

        public Music() {}

        // TODO: creating creators for each member gives me a headache.
        // TODO: check if I can use builder here
        public Music(String name) {
            this.name = name;
        }

        public Music(String name, String artist) {
            this.name = name;
            this.artist = artist;
        }

        public Music(String name, String artist, Time playTime) {
            this.name = name;
            this.artist = artist;
            this.playTime = playTime;
        }

        public Music(String name, String artist, Time playTime, Album album) {
            this.name = name;
            this.artist = artist;
            this.playTime = playTime;
            this.album = album;
        }
    }

    @Entity
    @Table(name="albums")
    public static class Album {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Getter
        private int id;

        @Column(name="name", nullable = false)
        @Getter @Setter
        private String name;

        // TODO: estimate overhead of @Prepersist and @postload
        @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
        @Getter @Setter
        private List<Music> musics;

        public Album() {}
        public Album(String name) {
            this.name = name;
        }

        public Album(String name, List<Music> musics) {
            this.name = name;
            this.musics = musics;
        }
    }

//    @Entity
//    @Table(name="users")
//    public static class User {
//        @Id
//        @GeneratedValue(strategy = GenerationType.AUTO)
//        private int id;
//
//        @Column(name="name")
//        @Getter @Setter
//        private String name;
//
//        @Column(name="playlist")
//        @Getter @Setter
//        private List<Music> playlist;
//
//        @Column(name="likes")
//        @Getter @Setter
//        private List<Music>
//
//    }

}

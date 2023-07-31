package dev.devs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
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
        @Getter @Setter
        private Album album;

        @ManyToMany(mappedBy = "musics")
        @Getter @Setter
        private List<Artist> artists;

        @ManyToMany(mappedBy = "musics")
        @Getter @Setter
        private List<Playlist> playlists;

        @OneToMany(mappedBy = "music")
        @Getter @Setter
        private List<MusicComment> musicComments;

        public Music() {}

        public Music(String name, Album album, List<Artist> artists, List<Playlist> playlists) {
            this.name = name;
            this.rating = 0.0;
            this.ratingCount = 0;
            this.album = album;
            this.artists = artists;
            this.playlists = playlists;
        }
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
        @Getter @Setter
        private List<Music> musics;

        @ManyToMany(mappedBy = "albums")
        @Getter @Setter
        private List<Artist> artists;

        @OneToMany(mappedBy = "album")
        @Getter @Setter
        private List<AlbumComment> albumComments;

        public Album () {}

        public Album(String name, List<Artist> artists) {
            this.name = name;
            this.rating = 0.0;
            this.ratingCount = 0;
            this.artists = artists;
        }

        public Album(String name) {
            this.name = name;
            this.rating = 0.0;
            this.ratingCount = 0;
        }

        public Album(String name, List<Music> musics, List<Artist> artists) {
            this.name = name;
            this.rating = 0.0;
            this.ratingCount = 0;
            this.musics = musics;
            this.artists = artists;
        }

        public void addArtist(Artist artist) {
            if (artists == null) artists = new ArrayList<>();
            artists.add(artist);
        }
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
        @Getter @Setter
        private List<Music> musics;

        @ManyToMany
        @JoinTable(
                name = "Artist_Album",
                joinColumns = @JoinColumn(name = "artist_id"),
                inverseJoinColumns = @JoinColumn(name = "album_id")
        )
        @Getter @Setter
        private List<Album> albums;

        @OneToMany(mappedBy = "artist")
        @Getter @Setter
        private List<ArtistComment> artistComments;

        public Artist() {}

        public Artist(String name) {
            this.name = name;
        }

        public void addAlbum(Album album) {
            if (albums == null) albums = new ArrayList<>();
            albums.add(album);
        }
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
        @Getter @Setter
        private List<MusicRating> musicRating;

        @OneToMany(mappedBy = "user")
        @Getter @Setter
        private List<MusicComment> musicComments;

        @OneToMany(mappedBy = "user")
        @Getter @Setter
        private List<AlbumRating> albumRating;

        @OneToMany(mappedBy = "user")
        @Getter @Setter
        private List<AlbumComment> albumComments;

        @OneToMany(mappedBy = "user")
        @Getter @Setter
        private List<ArtistComment> artistComments;

        public User() {}

        public User(String name) {
            this.name = name;
        }
    }

    @Entity
    @Table(name = "Playlist")
    public static class Playlist {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "name")
        @Getter @Setter
        private String name;

        @ManyToMany
        @JoinTable(
                name = "Playlist_Music",
                joinColumns = @JoinColumn(name = "playlist_id"),
                inverseJoinColumns = @JoinColumn(name = "music_id")
        )
        @Getter @Setter
        private List<Music> musics;

        public Playlist () {}
        public Playlist (String name, List<Music> musics) {
            this.name = name;
            this.musics = musics;
        }
    }

    @Entity
    @Table(name = "Music_Rating")
    public static class MusicRating {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "rating")
        @Getter @Setter
        private int rating;

        @Column(name = "timestamp")
        @Getter @Setter
        private Instant timestamp;

        @ManyToOne
        @JoinColumn(name = "music_id")
        @Getter @Setter
        private Music music;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @Getter @Setter
        private User user;

        public MusicRating() {}
        public MusicRating(int rating, Music music, User user) {
            if (rating < 0 || rating > 100) return; //Bad Request
            this.rating = rating;
            this.timestamp = Instant.now();
            this.music = music;
            this.user = user;
        }

    }

    @Entity
    @Table(name = "Music_Comment")
    public static class MusicComment {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "content")
        @Getter @Setter
        private String content;

        @Column(name = "timestamp")
        @Getter @Setter
        private Instant timestamp;

        @Column(name = "thumb_up")
        @Getter
        private long thumbUp;

        @Column(name = "thumb_down")
        @Getter
        private long thumbDown;

        @ManyToOne
        @JoinColumn(name = "music_id")
        @Getter @Setter
        private Music music;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @Getter @Setter
        private User user;

        public MusicComment() {}
        public MusicComment(String content, Music music, User user) {
            this.content = content;
            this.timestamp = Instant.now();
            this.music = music;
            this.user = user;
        }
    }

    @Entity
    @Table(name = "Album_Rating")
    public static class AlbumRating {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "rating")
        @Getter @Setter
        private int rating;

        @Column(name = "timestamp")
        @Getter @Setter
        private Instant timestamp;

        @ManyToOne
        @JoinColumn(name = "album_id")
        @Getter @Setter
        private Album album;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @Getter @Setter
        private User user;

        public AlbumRating() {}
        public AlbumRating(int rating, Album album, User user) {
            this.rating = rating;
            this.timestamp = Instant.now();
            this.album = album;
            this.user = user;
        }
    }


    @Entity
    @Table(name = "Album_Comment")
    public static class AlbumComment {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "content")
        @Getter @Setter
        private String content;

        @Column(name = "timestamp")
        @Getter @Setter
        private Instant timestamp;

        @Column(name = "thumb_up")
        @Getter @Setter
        private long thumbUp;

        @Column(name = "thumb_down")
        @Getter @Setter
        private long thumbDown;

        @ManyToOne
        @JoinColumn(name = "album_id")
        @Getter @Setter
        private Album album;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @Getter @Setter
        private User user;

        public AlbumComment() {}
        public AlbumComment(String content, Album album, User user) {
            this.content = content;
            this.timestamp = Instant.now();
            this.album = album;
            this.user = user;
        }
    }

    @Entity
    @Table(name = "Artist_Comment")
    public static class ArtistComment {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "content")
        @Getter @Setter
        private String content;

        @Column(name = "timestamp")
        @Getter @Setter
        private Instant timestamp;

        @Column(name = "thumb_up")
        @Getter @Setter
        private long thumbUp;

        @Column(name = "thumb_down")
        @Getter @Setter
        private long thumbDown;

        @ManyToOne
        @JoinColumn(name = "artist_id")
        @Getter @Setter
        private Artist artist;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @Getter @Setter
        private User user;

        public ArtistComment() {}
        public ArtistComment(String content, Artist artist, User user) {
            this.content = content;
            this.timestamp = Instant.now();
            this.artist = artist;
            this.user = user;
        }
    }

}

package dev.devs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

public class IMDbTable {
    static {

    }
    public interface ParsableTable {}

//    @Entity
//    @Table(name="title_akas")
//    public static class TitleAka implements ParsableTable {
//
//        private enum Type {
//            ALTERNATIVE, DVD, FESTIVAL, TV,
//            VIDEO, WORKING, ORIGINAL, IMDBDISPLAY
//        }
//        @Id
//        @Column(name="titleId")
//        private String titleId;
//
//        @Column(name="ordering")
//        @Getter @Setter
//        private int ordering;
//
//        @Column(name="title")
//        @Getter @Setter
//        private String title;
//
//        @Column(name="region")
//        @Getter @Setter
//        private String region;
//
//        @Column(name="language")
//        @Getter @Setter
//        private String language;
//
//        @ElementCollection(targetClass = Type.class)
//        @Enumerated(EnumType.STRING)
//        @CollectionTable()
//        @Column(name="types")
//        private Type[] types;
//
//
//
//
//
//    }
//
//    @Entity
//    @Table(name="title_basics")
//    public static class TitleBasic implements ParsableTable {
//
//    }
//
//    @Entity
//    @Table(name="title_crew")
//    public static class TitleCrew implements ParsableTable {
//
//    }
//
//    @Entity
//    @Table(name="title_episode")
//    public static class TitleEpisode implements ParsableTable {
//
//    }
//
//    @Entity
//    @Table(name="title_principals")
//    public static class TitlePrincipal implements ParsableTable {
//
//    }

    @Entity
    @Table(name="title_ratings")
    public static class TitleRating implements ParsableTable {
        // 1327947 records
        @Id
        // TODO: check for compatibility of Id and Column
        @Column(name="tconst")
        @Getter
        private String tconst;
        @Column(name="averageRating")
        @Getter @Setter
        private String averageRating;
        @Column(name="numVotes")
        @Getter @Setter
        private int numVotes;

        public TitleRating (String tconst, String averageRating, int numVotes) {
            this.tconst = tconst;
            this.averageRating = averageRating;
            this.numVotes = numVotes;
        }
    }
//
//    @Entity
//    @Table(name="name_basics")
//    public static class NameBasic implements ParsableTable {
//
//    }
//
    @Entity
    @Table(name="test")
    public static class Test {
        @Id
        @Column(name="id")
        @Setter
        @Getter
        private String id;
        @Column(name="name")
        @Getter
        @Setter
        private String name;
        @Column(name="uid")
        @Getter
        @Setter
        private int uid;

        public Test(String id, String name, int uid) {
            this.id = id;
            this.name = name;
            this.uid = uid;
        }
    }
//


}
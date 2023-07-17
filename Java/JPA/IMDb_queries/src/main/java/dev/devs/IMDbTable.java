package dev.devs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

public class IMDbTable {
    static {

    }
    interface ParsableTable {}

    @Entity
    @Table(name="title_ratings")
    public static class TitleRating implements ParsableTable {
        @Id
        // TODO: check for compatibility of Id and Column
        @Column(name="tconst")
        @Getter
        private String tconst;
        @Column(name="averageRating")
        private String averageRating;
        @Column(name="numVotes")
        private int numVotes;

        public TitleRating (String tconst, String averageRating, int numVotes) {
            this.tconst = tconst;
            this.averageRating = averageRating;
            this.numVotes = numVotes;
        }
    }

    @Entity
    @Table(name="test")
    public static class Test implements ParsableTable  {
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



}
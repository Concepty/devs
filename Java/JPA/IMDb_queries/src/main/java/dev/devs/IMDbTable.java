package dev.devs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

public class IMDbTable {
    static {

    }

    @Entity
    @Table(name="title_rating")
    public static class TitleRating {
        @Id
        // TODO: check for compatibility of Id and Column
        @Column(name="tconst")
        @Getter
        private String tconst;
        @Column(name="averageRating")
        private String averageRating;
        @Column(name="numVotes")
        private int numVotes;
    }

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



}
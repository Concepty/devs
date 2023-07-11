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



}

package dev.devs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

public class IMDbTable {
    static {

    }
    @Entity
    @Table(name="title_ratings")
    public static class TitleRating {
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

        public TitleRating(String tconst, String averageRating, int numVotes) {
            this.tconst = tconst;
            this.averageRating = averageRating;
            this.numVotes = numVotes;
        }
        public TitleRating(String[] cols) {
            if (cols == null || cols.length != 3) throw new RuntimeException("Not TitleRating record");
            this.tconst = cols[0];
            this.averageRating = cols[1];
            this.numVotes = Integer.parseInt(cols[2]);
        }
    }
}
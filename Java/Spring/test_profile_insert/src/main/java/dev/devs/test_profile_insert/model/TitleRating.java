package dev.devs.test_profile_insert.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Title_Rating")
public class TitleRating {
    @Id
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

    public TitleRating() {}
}

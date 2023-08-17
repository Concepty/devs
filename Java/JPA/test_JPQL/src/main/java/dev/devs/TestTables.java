

package dev.devs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

public class TestTables {
    static {

    }
    @Entity
    @Table(name="test_table")
    public static class TestTable {
        @Id
        @Column(name = "id")
        private long id;

        @Column(name="name")
        @Getter @Setter
        private String name;
        @Column(name="level")
        @Getter @Setter
        private int level;

        public TestTable () {}
        public TestTable (String name, int level) {
            this.name = name;
            this.level = level;
        }

    }
}
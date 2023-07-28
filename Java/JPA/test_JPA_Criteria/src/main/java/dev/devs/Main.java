package dev.devs;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.*;
import dev.devs.Tables.*;
import jakarta.persistence.criteria.*;

import java.util.Arrays;
import java.util.List;

/**
 * Testing JPA Criteria
 * Additionally, Complex Schema.
 * This project doesn't include fine-tuning data type.
 * Doing complex Schema. I need to learn how to make one-to-many and many-to-many relationship
 * in JPA. -> @OneToany
 *
 */

public class Main {
    // TODO: handle hibernate errro
    // error(Hibernate: alter table musics drop foreign key FKbysp2ixjy266hujxff4g7ic7a)
    // It happens when there is no tables in database. because Hibernate try to drop foreign keys first
    // before dropping tables.
    public static void main(String[] args) {
        JPAFunctions.runInsert((em -> {
            em.persist(new Music());
        }));
    }
}
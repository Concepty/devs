package dev.devs.test_profile_insert.util;

import dev.devs.test_profile_insert.model.TitleRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRatingRepository extends JpaRepository<TitleRating, Long> {
}

package dev.devs.test_profile_insert.service;

import dev.devs.test_profile_insert.model.TitleRating;
import dev.devs.test_profile_insert.util.TitleRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitleRatingService {
    @Autowired
    private TitleRatingRepository titleRatingRepository

    public TitleRating createTitleRating(TitleRating tr) {
        return titleRatingRepository.save(tr);
    }


}

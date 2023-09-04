package dev.devs.test_profile_insert.controller;

import dev.devs.test_profile_insert.model.TitleRating;
import dev.devs.test_profile_insert.service.TitleRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//TODO: titlerating name convention, Test.

@RestController
public class TitleRatingController {
    @Autowired
    private TitleRatingService titleRatingService;
    @PostMapping("/title_rating")
    public ResponseEntity<TitleRating> insertTitleRating(@RequestBody TitleRating tr) {
        TitleRating savedTitleRating = titleRatingService.createTitleRating(tr);
        return new ResponseEntity<>(savedTitleRating, HttpStatus.CREATED);
    }
}

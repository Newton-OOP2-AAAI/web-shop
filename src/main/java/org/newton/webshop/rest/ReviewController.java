package org.newton.webshop.rest;

import org.newton.webshop.models.entities.Review;
import org.newton.webshop.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/all")
    public Iterable<Review> findAll() {
        return reviewService.findAll();
    }

    @GetMapping
    Review findById(@RequestParam String id) {
        return reviewService.findById(id);
    }
}

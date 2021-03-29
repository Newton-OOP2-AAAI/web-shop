package org.newton.webshop.rest;

import org.newton.webshop.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Deprecated Should be removed, but keeping it for reference until AssortmentController is fully implemented
 * TODO Remove
 */
@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Commented out, but kept until class is removed
     */
//    @GetMapping("/all")
//    public List<Review> findAll() {
//        return reviewService.findAll();
//    }
//
//    @GetMapping
//    Review findById(@RequestParam String id) {
//        return reviewService.findById(id);
//    }
}

package org.newton.webshop.services;

import org.newton.webshop.models.entities.Review;
import org.newton.webshop.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(String id) {
        return reviewRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}

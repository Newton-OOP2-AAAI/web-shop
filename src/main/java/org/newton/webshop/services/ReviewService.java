package org.newton.webshop.services;

import org.newton.webshop.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Notes:
 * Lower level services that only handle one repository should return entities.
 * AssortmentService should take care of mapping between Entity/DTO
 */
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


}

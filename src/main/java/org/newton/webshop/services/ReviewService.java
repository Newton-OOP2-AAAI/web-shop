package org.newton.webshop.services;

import org.newton.webshop.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Notes:
     *
     * Lower level services that only handle one repository should return entities.
     * AssortmentService should take care of mapping between Entity/DTO
     *
     */


    //TODO commented code should be removed unless methods are needed in Assortment Service
//    public List<Review> findAll() {
//        return reviewRepository.findAll();
//    }
//
//    public Review findById(String id) {
//        return reviewRepository.findById(id).orElseThrow(RuntimeException::new);
//    }
}

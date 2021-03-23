package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, String> {

}

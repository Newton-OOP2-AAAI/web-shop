package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Twitter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import twitter4j.Trends;

@Repository
public interface TwitterRepository extends CrudRepository<Twitter, Trends> {

}

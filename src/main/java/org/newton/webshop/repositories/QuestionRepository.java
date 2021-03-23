package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, String> {

}

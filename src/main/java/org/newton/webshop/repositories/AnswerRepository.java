package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Answer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, String> {
    @Override
    List<Answer> findAll();
}

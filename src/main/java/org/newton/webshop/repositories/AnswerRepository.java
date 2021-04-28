package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Answer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface AnswerRepository extends CrudRepository<Answer, String> {
    @Override
    List<Answer> findAll();

    @Query("select a from Answer a inner join Question q on a.id = q.answer.id where q.questionText like ?1")
    Set<Answer> findAnswerByQuestionName(String question);
}

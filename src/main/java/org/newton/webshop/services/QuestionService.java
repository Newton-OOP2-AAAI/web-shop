package org.newton.webshop.services;

import org.newton.webshop.exceptions.QuestionNotFoundException;
import org.newton.webshop.models.entities.Question;
import org.newton.webshop.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    QuestionService(QuestionRepository repository) {
        this.questionRepository = repository;
    }

    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question createQuestion(Question newQuestion) {
        return questionRepository.save(newQuestion);
    }

    public Question findById(String id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
    }

    public Question replaceQuestion(Question newQuestion, String id) {
        return questionRepository.findById(id)
                .map(question -> {
                    question.setText(newQuestion.getText());
                    return questionRepository.save(question);
                })
                .orElseGet(() -> {
                    newQuestion.setId(id);
                    return questionRepository.save(newQuestion);
                });
    }

    public void deleteById(String id) {
        questionRepository.deleteById(id);
    }
}

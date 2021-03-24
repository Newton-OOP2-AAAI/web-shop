package org.newton.webshop.services;

import org.newton.webshop.exceptions.AnswerNotFoundException;
import org.newton.webshop.models.entities.Answer;
import org.newton.webshop.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    public Answer findById(String id) {
        return answerRepository.findById(id).orElseThrow(() -> new AnswerNotFoundException(id));
    }

    public Answer save(Answer newAnswer) {
        return answerRepository.save(newAnswer);
    }

    public Answer replaceAnswer(Answer newAnswer, String id) {
        return answerRepository.findById(id)
                .map(answer -> {
                    answer.setText(newAnswer.getText());
                    return answerRepository.save(answer);
                })
                .orElseGet(() -> {
                    newAnswer.setId(id);
                    return answerRepository.save(newAnswer);
                });
    }

    public void deleteById(String id) {
        answerRepository.deleteById(id);
    }
}

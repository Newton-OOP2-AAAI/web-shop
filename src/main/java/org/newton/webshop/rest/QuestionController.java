package org.newton.webshop.rest;

import org.newton.webshop.exceptions.QuestionNotFoundException;
import org.newton.webshop.models.Question;
import org.newton.webshop.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {
    private final QuestionRepository repository;

    @Autowired
    QuestionController(QuestionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/questions")
    public List<Question> all() {
        return repository.findAll();
    }

    @PostMapping("/questions")
    Question newQuestion(@RequestBody Question newQuestion) {
        return repository.save(newQuestion);
    }

    @GetMapping("/questions/{id}")
    Question one(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
    }

    @PutMapping("/questions/{id}")
    Question replaceQuestion(@RequestBody Question newQuestion, @PathVariable Integer id) {

        return repository.findById(id)
                .map(question -> {
                    question.setText(newQuestion.getText());
                    return repository.save(question);
                })
                .orElseGet(() -> {
                    newQuestion.setId(id);
                    return repository.save(newQuestion);
                });
    }

    @DeleteMapping("/questions/{id}")
    void deleteEmployee(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}

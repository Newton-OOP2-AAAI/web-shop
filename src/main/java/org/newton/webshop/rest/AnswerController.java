package org.newton.webshop.rest;

import org.newton.webshop.exceptions.AnswerNotFoundException;
import org.newton.webshop.models.Answer;
import org.newton.webshop.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnswerController {
    private final AnswerRepository repository;

    @Autowired
    AnswerController(AnswerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("answers")
    public List<Answer> all() {
        return repository.findAll();
    }

    @PostMapping("/answers")
    Answer newAnswer(@RequestBody Answer newAnswer) {
        return repository.save(newAnswer);
    }

    @GetMapping("/answers/{id}")
    Answer one(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException(id));
    }

    @PutMapping("/answers/{id}")
    Answer replaceAnswer(@RequestBody Answer newAnswer, @PathVariable Integer id) {

        return repository.findById(id)
                .map(answer -> {
                    answer.setText(newAnswer.getText());
                    return repository.save(answer);
                })
                .orElseGet(() -> {
                    newAnswer.setId(id);
                    return repository.save(newAnswer);
                });
    }

    @DeleteMapping("/answers/{id}")
    void deleteEmployee(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}


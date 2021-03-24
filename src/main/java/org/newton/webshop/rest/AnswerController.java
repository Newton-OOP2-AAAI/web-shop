package org.newton.webshop.rest;

import org.newton.webshop.models.entities.Answer;
import org.newton.webshop.services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/all")
    public List<Answer> findAll() {
        return answerService.findAll();
    }

    @GetMapping
    Answer findById(@RequestParam String id) {
        return answerService.findById(id);
    }

    @PostMapping
    Answer create(@RequestBody Answer newAnswer) {
        return answerService.save(newAnswer);
    }

    @PutMapping
    Answer replace(@RequestBody Answer newAnswer, @RequestParam String id) {
        return answerService.replaceAnswer(newAnswer, id);
    }

    @DeleteMapping
    void deleteEmployee(@RequestParam String id) {
        answerService.deleteById(id);
    }
}


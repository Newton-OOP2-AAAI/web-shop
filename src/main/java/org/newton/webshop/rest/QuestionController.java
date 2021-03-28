/*package org.newton.webshop.rest;

import org.newton.webshop.models.entities.Question;
import org.newton.webshop.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/all")
    List<Question> findAll() {
        return questionService.findAll();
    }

    @PostMapping()
    Question newQuestion(@RequestBody Question newQuestion) {
        return questionService.createQuestion(newQuestion);
    }

    @GetMapping
    Question findById(@RequestParam String id) {
        return questionService.findById(id);
    }

    @PutMapping
    Question replace(@RequestBody Question newQuestion, @RequestParam String id) {

        return questionService.replaceQuestion(newQuestion, id);
    }

    @DeleteMapping
    void delete(@PathVariable String id) {
        questionService.deleteById(id);
    }
}

 */

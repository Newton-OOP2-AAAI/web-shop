package org.newton.webshop.rest.combined;

import org.newton.webshop.models.dto.creation.AnswerCreationDto;
import org.newton.webshop.models.dto.creation.QuestionCreationDto;
import org.newton.webshop.models.dto.response.AnswerDto;
import org.newton.webshop.models.dto.update.AnswerUpdateDto;
import org.newton.webshop.services.combined.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Manages Chatbot
 * FAQ: "Frequently Asked Question" that the chatbot will respond to.
 * Each FAQ contains: Answer (response), questions (words/phrases to respond to) and a description
 *
 * @author Alex
 */
@RestController
@RequestMapping("/chatbot")
public class ChatbotController {
    private final ChatbotService chatbotService;

    @Autowired
    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    //todo ResponseEntity

    /**
     * Create an FAQ
     *
     * @param answerAndQuestionCreationDto json object that contains details about the FAQ
     * @return json object that contains details about answer and questions, including ids
     * @author Alex
     */
    @PostMapping("/answers")
    AnswerDto createFAQ(@RequestBody AnswerCreationDto answerAndQuestionCreationDto) {
        return chatbotService.create(answerAndQuestionCreationDto);
    }

    /**
     * Find FAQ by answer id
     *
     * @param id answer id
     * @return FAQ including answer id and all question ids
     * @author Alex
     */
    @GetMapping("/answers")
    AnswerDto findFAQbyId(@RequestParam String id) {
        return chatbotService.findById(id);
    }

    /**
     * Find all FAQs
     *
     * @return list of all FAQs including all related ids
     * @author Alex
     */
    @GetMapping("/answers/all")
    List<AnswerDto> findAllFAQs() {
        return chatbotService.findAll();
    }

    /**
     * Add a question to
     *
     * @param answerId            answer id that the question should be associated with
     * @param questionCreationDto requestbody with one field: questionText (String)
     * @return FAQ after the new question was added, including all related ids
     * @author Alex
     */
    @PostMapping("/answers/{answerId}/questions")
    AnswerDto addQuestionToFAQ(@PathVariable String answerId, @RequestBody QuestionCreationDto questionCreationDto) {
        return chatbotService.createQuestion(answerId, questionCreationDto);
    }

    /**
     * Removes a question from an FAQ
     *
     * @param id question id
     * @author Alex
     */
    @DeleteMapping("/answers/questions")
    void deleteQuestion(@RequestParam String id) {
        chatbotService.deleteQuestion(id);
    }

    /**
     * Update an answer
     *
     * @param id        answer id
     * @param updateDto json object with two fields: description (String), answerText (String)
     * @return FAQ after the answer was updated, including all related ids
     * @author Alex
     */
    @PutMapping("/answers")
    AnswerDto replaceAnswer(@RequestParam String id, @RequestBody AnswerUpdateDto updateDto) {
        return chatbotService.replaceAnswer(id, updateDto);
    }

    @DeleteMapping("/answers")
    void deleteFAQ(@RequestParam String id) {
        chatbotService.deleteFAQ(id);
    }
}

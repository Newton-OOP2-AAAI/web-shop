package org.newton.webshop.rest;

import org.newton.webshop.models.dto.creation.AnswerCreationDto;
import org.newton.webshop.models.dto.creation.QuestionCreationDto;
import org.newton.webshop.models.dto.response.AnswerSimpleDto;
import org.newton.webshop.models.dto.response.FaqDto;
import org.newton.webshop.models.dto.update.AnswerUpdateDto;
import org.newton.webshop.services.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Manages Chatbot
 * FAQ: "Frequently Asked Question" that the chatbot will respond to.
 * Each FAQ contains: Answer (response), questions (words/phrases to respond to) and a description
 */
@RestController
@RequestMapping("/chatbot")
public class ChatbotController {
    private final ChatbotService chatbotService;

    @Autowired
    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    /**
     * Find FAQ by answer id
     *
     * @param id answer id
     * @return FAQ including answer id and all question ids
     */
    @GetMapping(path = "/answers", params = {"id"}, produces = MediaType.APPLICATION_JSON_VALUE)
    FaqDto findFAQbyId(@RequestParam String id) {
        return chatbotService.findFaqById(id);
    }

    /**
     * Find all FAQs
     *
     * @return list of all FAQs including all related ids
     */
    @GetMapping(path = "/answers", produces = MediaType.APPLICATION_JSON_VALUE)
    List<FaqDto> findAllFAQs() {
        return chatbotService.findAllFaq();
    }

    /**
     * Find answer text by question text
     *
     * @param question phrase/text
     * @return dto containing answer text
     */
    @GetMapping(path = "/answers", params = {"question"})
    Set<AnswerSimpleDto> findAnswerByQuestionName(@RequestParam String question) {
        return chatbotService.findAnswerByQuestionText(question);
    }

    /**
     * Create FAQ
     *
     * @param creationDto json object that contains details about the FAQ
     * @return json object that contains details about answer and questions, including ids
     */
    @PostMapping(path = "/answers", produces = MediaType.APPLICATION_JSON_VALUE)
    FaqDto createFAQ(@RequestBody AnswerCreationDto creationDto) {
        return chatbotService.createFaq(creationDto);
    }

    /**
     * Add a question to FAQ
     *
     * @param answerId            answer id that the question should be associated with
     * @param questionCreationDto requestbody with one field: questionText (String)
     * @return FAQ after the new question was added, including all related ids
     */
    @PostMapping(path = "/answers/{answerId}/questions", produces = MediaType.APPLICATION_JSON_VALUE)
    FaqDto addQuestionToFAQ(@PathVariable String answerId, @RequestBody QuestionCreationDto questionCreationDto) {
        return chatbotService.createQuestion(answerId, questionCreationDto);
    }

    /**
     * Update an answer in FAQ
     *
     * @param id        answer id
     * @param updateDto json object with two fields: description (String), answerText (String)
     * @return FAQ after the answer was updated, including all related ids
     */
    @PutMapping(path = "/answers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    FaqDto updateAnswer(@PathVariable String id, @RequestBody AnswerUpdateDto updateDto) {
        return chatbotService.updateAnswer(id, updateDto);
    }

    /**
     * Delete FAQ (including answer and questions)
     *
     * @param answerId answer id
     */
    @DeleteMapping(path = "/answers/{answer_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteFAQ(@PathVariable(name = "answer_id") String answerId) {
        chatbotService.deleteFaq(answerId);
    }

    /**
     * Delete a question in an FAQ
     *
     * @param questionId question id
     */
    @DeleteMapping(path = "/answers/questions/{question_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteQuestion(@PathVariable(name = "question_id") String questionId) {
        chatbotService.deleteQuestion(questionId);
    }
}

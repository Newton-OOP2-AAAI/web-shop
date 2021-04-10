package org.newton.webshop.services.combined;

import org.newton.webshop.models.dto.creation.AnswerCreationDto;
import org.newton.webshop.models.dto.creation.QuestionCreationDto;
import org.newton.webshop.models.dto.response.AnswerDto;
import org.newton.webshop.models.dto.update.AnswerUpdateDto;
import org.newton.webshop.models.entities.Answer;
import org.newton.webshop.models.entities.Question;
import org.newton.webshop.repositories.AnswerRepository;
import org.newton.webshop.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles requests from ChatbotController.
 * FAQ: "Frequently Asked Question" that the chatbot will respond to.
 * Each FAQ contains: Answer (chatbot response), questions (words/phrases that trigger the response) and a description
 */
@Service
public class ChatbotService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    //todo replace general RuntimeException with custom exceptions and error handling

    @Autowired
    public ChatbotService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    /**
     * Create one FAQ.
     *
     * @param creationDto contains info about the entities to create
     * @return dto that contains the created entities and their ids
     */
    public AnswerDto createFaq(AnswerCreationDto creationDto) {
        //AnswerCreationDto to Answer using constructor, then persist
        Answer answer = answerRepository.save(toEntity(creationDto));

        //Set<String> to Set<Questions>
        var questions = creationDto.getQuestions()
                .stream()
                .map(ChatbotService::toEntity)
                .collect(Collectors.toSet());

        //associate each question with answer and persist question
        questions.forEach(question -> {
            question.setAnswer(answer);
            questionRepository.save(question);
        });

        //associate answer with questions
        answer.setQuestions(questions);

        //return answerdto
        return toDto(answer);
    }


    /**
     * Find FAQ by answer id
     *
     * @param id answer id
     * @return dto, containing the info in the created FAQ and the respective ids
     */
    public AnswerDto findFaqById(String id) {
        var answer = answerRepository.findById(id).orElseThrow(RuntimeException::new);
        return toDto(answer);
    }


    /**
     * Find all FAQ pairs
     *
     * @return list of all FAQs, each one contained in a dto
     */
    public List<AnswerDto> findAllFaq() {
        return answerRepository.findAll()
                .stream()
                .map(ChatbotService::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Create another question phrase/word in an existing FAQ.
     *
     * @param answerId            answer id
     * @param questionCreationDto dto containing info required to create question
     * @return dto, containing the info in the created question and the respective ids
     */
    public AnswerDto createQuestion(String answerId, QuestionCreationDto questionCreationDto) {
        //find answer to create the question for
        var answer = answerRepository.findById(answerId).orElseThrow(RuntimeException::new);

        //creationdto to question
        var question = toEntity(questionCreationDto);

        //set associations
        answer.addQuestion(question);

        //persist question
        questionRepository.save(question);

        //return AnswerDto
        return toDto(answer);
    }

    /**
     * Delete question phrase/word from FAQ.
     *
     * @param questionId id of question to delete
     */
    public void deleteQuestion(String questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(RuntimeException::new);
        questionRepository.delete(question);
    }

    /**
     * Update answer in FAQ.
     *
     * @param id        id of answer
     * @param updateDto dto containing info needed to update answer
     * @return dto representing FAQ after the answer was updated
     */
    public AnswerDto updateAnswer(String id, AnswerUpdateDto updateDto) {
        Answer updatedAnswer = answerRepository.findById(id).map(answer -> {
            answer.setAnswerText(updateDto.getAnswerText());
            answer.setDescription(updateDto.getDescription());
            return answerRepository.save(answer);
        }).orElseThrow(RuntimeException::new);

        return toDto(updatedAnswer);
    }

    /**
     * Delete FAQ including the answer entity and all question entities
     *
     * @param id answer id
     */
    public void deleteFaq(String id) {
        Answer answer = answerRepository.findById(id).orElseThrow(RuntimeException::new);
        answerRepository.delete(answer);
    }

    /**
     * Converts Answer entity to a response dto. The set of Question entities in the Answer entity is mapped to a Hashmap (key = question id, value = question text)
     *
     * @param answer entity to convert
     * @return response dto
     */
    private static AnswerDto toDto(Answer answer) {
        return AnswerDto.builder()
                .id(answer.getId())
                .description(answer.getDescription())
                .questions(answer.getQuestions()
                        .stream()
                        .collect(Collectors.toMap(Question::getId, Question::getQuestionText)))
                .answerText(answer.getAnswerText())
                .build();
    }

    /**
     * Converts dto to Question entity. Leaves answer-field as null. Leaves id-field as null.
     *
     * @param creationDto dto containing required details to create a question entity
     * @return Question entity
     */
    private static Question toEntity(QuestionCreationDto creationDto) {
        return Question.builder()
                .questionText(creationDto.getQuestionText())
                .build();
    }

    /**
     * Converts dto to Question entity. Leaves answer-field as null. Leaves id-field as null.
     * This method is an alternative to the toEntity-method that take a QuestionCreationDto (which currently only contains one field).
     * See documentation on org.newton.webshop.models.dto.creation.AnswerCreationDto.java for more information.
     *
     * @param questionText the question phrase/text
     * @return Question entity
     */
    private static Question toEntity(String questionText) {
        return Question.builder()
                .questionText(questionText)
                .build();
    }

    /**
     * Converts dto to Answer entity. Leaves questions-field as empty HashSet. Leaves id-field as null.
     * This method is an alternative to the toEntity-method that take a QuestionCreationDto (which currently only contains one field).
     * See documentation on org.newton.webshop.models.dto.creation.AnswerCreationDto.java for more information.
     *
     * @param creationDto
     * @return
     */
    private static Answer toEntity(AnswerCreationDto creationDto) {
        return Answer.builder()
                .questions(new HashSet<>())
                .answerText(creationDto.getAnswerText())
                .description(creationDto.getDescription())
                .build();
    }
}

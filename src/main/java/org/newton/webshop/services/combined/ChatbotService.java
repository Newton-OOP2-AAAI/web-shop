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
    public AnswerDto create(AnswerCreationDto creationDto) {
        //AnswerCreationDto to Answer using constructor, then persist
        Answer answer = answerRepository.save(new Answer(creationDto));

        //Set<String> to Set<Questions>
        var questions = creationDto.getQuestions()
                .stream()
                .map(Question::new)
                .collect(Collectors.toSet());

        //associate each question with answer and persist question
        questions.forEach(question -> {
            question.setAnswer(answer);
            questionRepository.save(question);
        });

        //associate answer with questions
        answer.setQuestions(questions);

        //return answerdto
        return new AnswerDto(answer);
    }

    /**
     * Find FAQ by answer id
     *
     * @param id answer id
     * @return dto, containing the info in the created FAQ and the respective ids
     */
    public AnswerDto findById(String id) {
        return new AnswerDto(answerRepository.findById(id).orElseThrow(RuntimeException::new));
    }


    /**
     * Find all FAQ pairs
     *
     * @return list of all FAQs, each one contained in a dto
     */
    public List<AnswerDto> findAll() {
        return answerRepository.findAll()
                .stream()
                .map(AnswerDto::new)
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
        Answer answer = answerRepository.findById(answerId).orElseThrow(RuntimeException::new);

        //creationdto to question
        Question question = new Question(questionCreationDto);

        //set associations
        question.setAnswer(answer);
        answer.addQuestion(question);

        //persist question
        questionRepository.save(question);

        //return AnswerDto
        return new AnswerDto(answer);
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

        return new AnswerDto(updatedAnswer);
    }

    /**
     * Delete FAQ including the answer entity and all question entities
     *
     * @param id answer id
     */
    public void deleteFAQ(String id) {
        Answer answer = answerRepository.findById(id).orElseThrow(RuntimeException::new);
        answerRepository.delete(answer);
    }
}

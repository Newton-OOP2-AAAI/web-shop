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

@Service
public class ChatbotService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public ChatbotService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public AnswerDto create(AnswerCreationDto creationDto) {
        //AnswerCreationDto to Answer using constructor, then persist
        Answer answer = answerRepository.save(new Answer(creationDto));

        //Set<String> to Questions, associate with answer and persist each question
        creationDto.getQuestions()
                .stream()
                .map(Question::new)
                .forEach(question -> {
                    question.addAnswer(answer); //todo look into how this can (or if it should) be solved without Shared mutability
                    questionRepository.save(question);
                });
        return new AnswerDto(answer);
    }

    public AnswerDto findById(String id) {
        return new AnswerDto(answerRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    public List<AnswerDto> findAll() {
        return answerRepository.findAll()
                .stream()
                .map(AnswerDto::new)
                .collect(Collectors.toList());
    }

    public AnswerDto createQuestion(String answerId, QuestionCreationDto questionCreationDto) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(RuntimeException::new);
        Question question = new Question(questionCreationDto);
        question.addAnswer(answer);
        questionRepository.save(question);
        return new AnswerDto(answer);
    }

    public void deleteQuestion(String questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(RuntimeException::new); //todo return something instead?
        questionRepository.delete(question);
    }

    public AnswerDto replaceAnswer(String id, AnswerUpdateDto updateDto) {
        Answer updatedAnswer = answerRepository.findById(id).map(answer -> {
            answer.setAnswerText(updateDto.getAnswerText());
            answer.setDescription(updateDto.getDescription());
            return answerRepository.save(answer);
        }).orElseThrow(RuntimeException::new);

        return new AnswerDto(updatedAnswer);
    }

    public void deleteFAQ(String id) {
        Answer answer = answerRepository.findById(id).orElseThrow(RuntimeException::new);
        answerRepository.delete(answer);
    }
}

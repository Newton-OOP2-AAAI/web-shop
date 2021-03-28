package org.newton.webshop.models.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.newton.webshop.models.entities.Answer;
import org.newton.webshop.models.entities.Question;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class AnswerDto {
    private String id;
    private String description;
    private Map<String, String> questions;
    private String answerText;

    public AnswerDto(Answer answer) {
        this.id = answer.getId();
        this.description = answer.getDescription();
        this.questions = answer.getQuestions()
                .stream()
                .collect(Collectors.toMap(Question::getId, Question::getQuestionText));
        this.answerText = answer.getAnswerText();
    }
}

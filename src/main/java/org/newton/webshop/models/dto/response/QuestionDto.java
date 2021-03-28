package org.newton.webshop.models.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.newton.webshop.models.entities.Question;

@Getter
@Setter
public class QuestionDto {
    private String id;
    private String questionText;

    public QuestionDto() {
    }

    public QuestionDto(Question question) {
        this.id = question.getId();
        this.questionText = question.getQuestionText();
    }
}

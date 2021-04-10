package org.newton.webshop.models.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Contains fields necessary to create one Answer entity and multiple Question entities.
 * There is a QuestionCreationDto, but an extra nested object seemed unnecessary while it only contains one field.
 * That's why questions is just a set of strings.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerCreationDto {
    private String description;
    private Set<String> questions;
    private String answerText;
}

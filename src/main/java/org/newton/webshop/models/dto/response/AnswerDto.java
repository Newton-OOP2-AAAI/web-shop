package org.newton.webshop.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class AnswerDto {

    private String id;
    private String description;
    private Map<String, String> questions;
    private String answerText;
}

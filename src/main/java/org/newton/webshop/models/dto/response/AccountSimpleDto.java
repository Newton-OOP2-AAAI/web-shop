package org.newton.webshop.models.dto.response;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
public class AccountSimpleDto {
    private String username;
    private LocalDate birthDate;
}

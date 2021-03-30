package org.newton.webshop.models.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.newton.webshop.models.entities.Account;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class AccountDetailsDto {

    private String username;
    private LocalDate birthDate;

    public AccountDetailsDto(Account account) {
        this.username = account.getUsername();
        this.birthDate = account.getBirthDate();
    }
}

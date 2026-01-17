package br.com.carlos.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "Email is required")
    @Pattern(
            regexp = "^[^@]*@[^@]*$",
            message = "The field must contain exactly one '@'")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}

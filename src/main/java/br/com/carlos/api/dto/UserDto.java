package br.com.carlos.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @NotBlank(message = "Name is required")
    private String name;

    private String surname;

    @NotBlank(message = "User is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Pattern(
            regexp = "^[^@]*@[^@]*$",
            message = "The field must contain exactly one '@'")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Phone is required")
    private String phone;

    public UserDto(String name, String surname, String username, String email, String password, String phone) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}

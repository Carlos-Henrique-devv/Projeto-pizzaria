package br.com.carlos.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    private String email;
    private String senha;

    public LoginRequestDto(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }
}

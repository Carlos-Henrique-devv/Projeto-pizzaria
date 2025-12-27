package br.com.carlos.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {

    @NotBlank(message = "Nome e obrigatorio")
    private String nome;

    private String sobrenome;

    @NotBlank(message = "UserName e obrigatorio")
    private String username;

    @NotBlank(message = "Email e obrigatorio")
    @Pattern(
            regexp = "^[^@]*@[^@]*$",
            message = "O campo deve conter exatamente um '@'"
    )
    private String email;

    @NotBlank(message = "Senha e obrigatoria")
    private String senha;

    @NotBlank(message = "Telefone e obrigatorio")
    private String telefone;

    public UsuarioDto(String nome, String sobrenome, String username, String email, String senha, String telefone) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
    }
}

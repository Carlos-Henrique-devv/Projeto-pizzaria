package br.com.carlos.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "sobrenome", length = 200)
    private String sobrenome;

    @Column(name = "username", length = 100, nullable = false)
    private String username;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "senha", length = 100, nullable = false)
    private String senha;

    @Column(name = "telefone", length = 15, nullable = false)
    private String telefone;
}

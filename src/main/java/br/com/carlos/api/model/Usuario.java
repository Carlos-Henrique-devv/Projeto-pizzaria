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

    @Column(name = "nome", length = 200, nullable = false, unique = false)
    private String nome;

    @Column(name = "Sobrenome", length = 200, nullable = true, unique = false)
    private String sobrenome;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "senha", length = 100, nullable = false, unique = true)
    private String senha;

    @Column(name = "telefone", length = 15, nullable = false, unique = true)
    private String telefone;
}

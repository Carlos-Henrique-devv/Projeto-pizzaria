package br.com.carlos.api.controller;

import br.com.carlos.api.dto.LoginRequestDto;
import br.com.carlos.api.dto.UsuarioDto;
import br.com.carlos.api.model.Usuario;
import br.com.carlos.api.repository.IUsuario;
import br.com.carlos.api.token.Token;
import br.com.carlos.api.token.TokenUtil;
import br.com.carlos.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final IUsuario iUsuario;

    public UsuarioController(UsuarioService usuarioService, IUsuario iUsuario) {
        this.usuarioService = usuarioService;
        this.iUsuario = iUsuario;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios() {
        return ResponseEntity.status(200).body(usuarioService.listarUsuario());
    }

    @PostMapping
    public ResponseEntity<?> saveUsuario(@RequestBody @Valid UsuarioDto usuarioDto) {
        try {
            return ResponseEntity.status(201).body(usuarioService.salvaUsuario(usuarioDto));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error ao cria o usuário");
        }
    }

    @PutMapping
    public ResponseEntity<?> editarUsuario(@RequestBody @Valid UsuarioDto usuarioDto) {
        try {
            return ResponseEntity.status(201).body(usuarioService.editarUsuario(usuarioDto));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao altera o usuário");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Integer id) {
        boolean deletado = usuarioService.excluirUsuario(id);

        if (deletado) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> validarSenha(@RequestBody LoginRequestDto loginRequestDto) {
        Boolean valid = usuarioService.validarSenha(loginRequestDto);
        Optional<Usuario> optFindByEmail = iUsuario.findByEmail(loginRequestDto.getEmail());

        if (!valid) {
            return ResponseEntity.status(404).body("Senha ou email incorreto");
        }

        Usuario usuario = optFindByEmail.get();
        String createToken = TokenUtil.createToken(usuario);
        Token token = new Token(createToken);

        return ResponseEntity.ok(token);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExeption(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fiedName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fiedName, errorMessage);
        });
        return errors;
    }
}

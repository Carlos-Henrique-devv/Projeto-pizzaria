package br.com.carlos.api.controller;

import br.com.carlos.api.dto.LoginRequest;
import br.com.carlos.api.model.Usuario;
import br.com.carlos.api.repository.IUsuario;
import br.com.carlos.api.token.Token;
import br.com.carlos.api.token.TokenUtil;
import br.com.carlos.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private IUsuario iUsuario;

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios() {
        return ResponseEntity.status(200).body(usuarioService.listarUsuario());
    }

    @PostMapping
    public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioService.salvaUsuario(usuario);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @PutMapping
    public ResponseEntity<Usuario> editarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(201).body(usuarioService.editarUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Integer id) {
        boolean deletado = usuarioService.excluirUsuario(id);

        if (deletado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> validarSenha(@RequestBody LoginRequest loginRequest) {
        Boolean valid = usuarioService.validarSenha(loginRequest);
        Optional<Usuario> optFindByEmail = iUsuario.findByEmail(loginRequest.getEmail());

        if (!valid) {
            return ResponseEntity.status(401).build();
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

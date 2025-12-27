package br.com.carlos.api.service;

import br.com.carlos.api.dto.LoginRequestDto;
import br.com.carlos.api.dto.UsuarioDto;
import br.com.carlos.api.model.Usuario;
import br.com.carlos.api.repository.IUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuario iUsuario;

    private final PasswordEncoder passwordEncoder;

    public UsuarioService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<Usuario> listarUsuario() {
        return iUsuario.findAll();
    }

    public Usuario salvaUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDto.getNome());
        usuario.setSobrenome(usuarioDto.getSobrenome());
        usuario.setUsername(usuarioDto.getUsername());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        usuario.setTelefone(usuarioDto.getTelefone());
        return iUsuario.save(usuario);
    }

    public Usuario editarUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDto.getNome());
        usuario.setSobrenome(usuarioDto.getSobrenome());
        usuario.setUsername(usuarioDto.getUsername());
        usuario.setSenha(usuarioDto.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        usuario.setTelefone(usuarioDto.getTelefone());
        return iUsuario.save(usuario);
    }

    public boolean excluirUsuario(Integer id) {
        if (iUsuario.existsById(id)) {
            iUsuario.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Boolean validarSenha(LoginRequestDto loginRequestDto) {
        Optional<Usuario> usuarioOpt = iUsuario.findByEmail(loginRequestDto.getEmail());

        if (usuarioOpt.isEmpty()) {
            return false;
        }

        Usuario usuarioDB = usuarioOpt.get();
        boolean senhaCorreta = passwordEncoder.matches(loginRequestDto.getSenha(), usuarioDB.getSenha());

        return senhaCorreta;
    }
}

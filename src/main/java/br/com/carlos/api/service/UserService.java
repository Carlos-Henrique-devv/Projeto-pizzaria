package br.com.carlos.api.service;

import br.com.carlos.api.dto.LoginRequestDto;
import br.com.carlos.api.dto.UserDto;
import br.com.carlos.api.exception.*;
import br.com.carlos.api.exception.duplicate.DuplicateEmailException;
import br.com.carlos.api.exception.duplicate.DuplicateFiedException;
import br.com.carlos.api.exception.duplicate.DuplicatePhoneException;
import br.com.carlos.api.exception.duplicate.DuplicateUserNameException;
import br.com.carlos.api.exception.IdNotFoundException;
import br.com.carlos.api.model.User;
import br.com.carlos.api.repository.IUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final IUser iUser;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUser iUser) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.iUser = iUser;
    }

    public List<User> get() {
        return iUser.findAll();
    }

    public User save(UserDto userDto) {
        if (iUser.existsByUsername(userDto.getUsername())) {
            throw new DuplicateUserNameException("Username already exists");
        }

        if (iUser.existsByEmail(userDto.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }

        if (iUser.existsByPhone(userDto.getPhone())) {
            throw new DuplicatePhoneException("Phone already exists");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhone(userDto.getPhone());
        return iUser.save(user);
    }

    public User update(UserDto userDto) {
        User user = iUser.findById(userDto.getId())
                .orElseThrow(() -> new IdNotFoundException("Id not found"));

        boolean notChanged = Objects.equals(user.getName(), userDto.getName())
                && Objects.equals(user.getSurname(), userDto.getSurname())
                && Objects.equals(user.getUsername(), userDto.getUsername())
                && Objects.equals(user.getEmail(), userDto.getEmail())
                && passwordEncoder.matches(userDto.getPassword(), user.getPassword())
                && Objects.equals(user.getPhone(), userDto.getPhone());

        if (!user.getEmail().equals(userDto.getEmail()) && iUser.existsByEmail(userDto.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }

        if (notChanged) {
            throw new DuplicateFiedException("Change a field");
        }

        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhone(userDto.getPhone());

        return iUser.save(user);
    }

    public boolean deleter(Integer id) {
        if (iUser.existsById(id)) {
            iUser.deleteById(id);
            return true;
        } else {
            throw new IdNotFoundException("Id not existe");
        }
    }

    public boolean valid(LoginRequestDto loginRequestDto) {
        Optional<User> usuarioOpt = iUser.findByEmail(loginRequestDto.getEmail());

        if (usuarioOpt.isEmpty()) {
            throw new ValidException("Incorrect email or password");
        }

        User userDB = usuarioOpt.get();
        boolean senhaCorreta = passwordEncoder.matches(loginRequestDto.getPassword(), userDB.getPassword());

        if (!senhaCorreta) {
            throw new ValidException("Incorrect email or password");
        }

        return true;
    }
}

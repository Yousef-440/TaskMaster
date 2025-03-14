package com.tasks.Tasks.service;

import com.tasks.Tasks.exception.InvalidException;
import com.tasks.Tasks.exception.InvalidPasswordException;
import com.tasks.Tasks.exception.UserFoundException;
import com.tasks.Tasks.model.UserEntity;
import com.tasks.Tasks.repository.TaskEntityRepo;
import com.tasks.Tasks.repository.UserEntityRepo;
import com.tasks.Tasks.security.JWTGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.regex.Pattern;

@Service
public class AuthService {

    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TaskEntityRepo taskEntityRepo;
    @Autowired
    private UserEntityRepo userEntityRepo;

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{9,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private void validatePassword(String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new InvalidPasswordException("Password must be at least 9 characters long and contain letters (uppercase and lowercase), numbers, and special characters.");
        }
    }

    @Transactional
    public void createUser(UserEntity user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new InvalidException("Username is required.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new InvalidException("Password is required.");
        }
        validatePassword(user.getPassword());
        if (userEntityRepo.existsByUsername(user.getUsername())) {
            throw new UserFoundException("The username already exists. Please choose another name.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userEntityRepo.save(user);
    }
}

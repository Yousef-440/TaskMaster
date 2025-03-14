package com.tasks.Tasks.service;

import com.tasks.Tasks.dto.AuthResponseDTO;
import com.tasks.Tasks.dto.UserDto;
import com.tasks.Tasks.exception.*;
import com.tasks.Tasks.model.UserEntity;
import com.tasks.Tasks.repository.TaskEntityRepo;
import com.tasks.Tasks.repository.UserEntityRepo;
import com.tasks.Tasks.security.JWTGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {


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

    public UserEntity getUserById(int id){
        UserEntity user = userEntityRepo.findById(id)
                .orElseThrow(()->new UserNotFoundException("User Not Found"));

        return user;
    }

    public UserEntity updateUserById(int id ,UserEntity user){
        UserEntity userEntity  = userEntityRepo.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found"));
        userEntity.setUsername(user.getUsername());

        if(!userEntityRepo.existsByUsername(user.getUsername())){
            return userEntityRepo.save(userEntity);
        }
        else{
            throw new UserFoundException("The name exists, please choose another name.");
        }
    }

    @Transactional
    public void updateFullUserById(int id, UserDto user) {
        UserEntity userEntity = userEntityRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        String CurrentPass = userEntity.getPassword();
        String Pass = user.getOldPassword();
        if (!CurrentPass.equals(Pass)) {
            throw new PasswordNotMatchException("Password does not match");
        }

        if (!userEntity.getUsername().equals(user.getUsername())) {
            if (userEntityRepo.existsByUsername(user.getUsername())) {
                throw new UserFoundException("The username already exists. Please choose another name.");
            }
        }
        validatePassword(user.getNewPassword());

        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getNewPassword());

        userEntityRepo.save(userEntity);
    }

    public void deleteUserById(int id){
        UserEntity user = userEntityRepo.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not Found"));
        userEntityRepo.deleteById(id);
    }
}

package com.tasks.Tasks.controller;

import com.tasks.Tasks.dto.AuthResponseDTO;
import com.tasks.Tasks.model.UserEntity;
import com.tasks.Tasks.security.JWTGenerator;
import com.tasks.Tasks.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private AuthService authService;
//Done
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserEntity user) {
        authService.createUser(user);
        return new ResponseEntity<>("User Created Successfully" , HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> Login(@RequestBody UserEntity user){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponseDTO(token) , HttpStatus.OK);
    }
}

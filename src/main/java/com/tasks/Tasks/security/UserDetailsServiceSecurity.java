package com.tasks.Tasks.security;

import com.tasks.Tasks.exception.UserNotFoundException;
import com.tasks.Tasks.model.UserEntity;
import com.tasks.Tasks.repository.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceSecurity implements UserDetailsService {

    @Autowired
    private UserEntityRepo userEntityRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user =userEntityRepo.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException("User not Found"));

        return new User(user.getUsername() , user.getPassword(),new ArrayList<>());
    }
}

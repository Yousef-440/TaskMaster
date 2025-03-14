package com.tasks.Tasks.controller;

import com.tasks.Tasks.dto.UserDto;
import com.tasks.Tasks.model.UserEntity;
import com.tasks.Tasks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService service;



    @PutMapping("update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id
            ,@RequestBody UserEntity user
            ,@AuthenticationPrincipal UserDetails userDetails){

        String loggedInUsername = userDetails.getUsername();
        UserEntity userEntity = service.getUserById(id);
        if (!loggedInUsername.equals(userEntity.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to edit this user.");
        }

        service.updateUserById(id , user);
        return new ResponseEntity<>("User Updated Successfully" , HttpStatus.OK);
    }

    @PutMapping("update/password/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable int id , @RequestBody UserDto user,
                                                 @AuthenticationPrincipal UserDetails userDetails){

        String loggedInUsername = userDetails.getUsername();
        UserEntity userEntity = service.getUserById(id);
        if (!loggedInUsername.equals(userEntity.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to edit this user.");
        }
        service.updateFullUserById(id,user);
        return new ResponseEntity<>("username and password updated successfully" , HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable int id,@AuthenticationPrincipal UserDetails userDetails){
        String loggedInUsername = userDetails.getUsername();
        UserEntity userEntity = service.getUserById(id);
        if (!loggedInUsername.equals(userEntity.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to edit this user.");
        }
        service.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}

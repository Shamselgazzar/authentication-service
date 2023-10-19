package com.sumerge.authservice.controller;

import com.sumerge.authservice.entity.User;
import com.sumerge.authservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User newUser) {

        User registerUser = userService.registerNewUser(newUser);

        return  ResponseEntity.ok()
                .body(registerUser.getEmail() + " : has been registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User newUser) {

        String token = userService.authenticateUser(newUser);

        return ResponseEntity.ok().body(token);
    }


}


//generate key::
//return secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
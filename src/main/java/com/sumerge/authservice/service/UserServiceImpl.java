package com.sumerge.authservice.service;

import com.sumerge.authservice.entity.Role;
import com.sumerge.authservice.entity.User;
import com.sumerge.authservice.exceptions.NotValidMailException;
import com.sumerge.authservice.exceptions.UserNotFoundException;
import com.sumerge.authservice.repository.UserRepository;
import com.sumerge.authservice.security.JwtGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtGeneratorService jwtGeneratorService;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtGeneratorService jwtGeneratorService) {
        this.userRepository = userRepository;
        this.jwtGeneratorService = jwtGeneratorService;
    }

    private final  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public User registerNewUser(User newUser) {

        if( !checkEmailValidity(newUser.getEmail())){
            throw new NotValidMailException("invalid email format");
        }
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if(newUser.getPassword().isEmpty()){
            throw new IllegalArgumentException("Empty password is not allowed");
        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRole(Role.USER);

        return userRepository.save(newUser);
    }

    @Override
    public String authenticateUser(User newUser) {
        Optional<User> userOptional = userRepository.findByEmail(newUser.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(newUser.getPassword(), user.getPassword())) {
                return jwtGeneratorService.createToken(user);
            }else {
                throw new IllegalArgumentException("Bad Credentials");
            }
        }else {
            throw new UserNotFoundException("This email is not registered!");
        }
    }

    public Boolean checkEmailValidity(String email) {

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }



}

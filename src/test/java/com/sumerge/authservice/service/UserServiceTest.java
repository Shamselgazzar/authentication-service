package com.sumerge.authservice.service;

import com.sumerge.authservice.entity.User;
import com.sumerge.authservice.exceptions.NotValidMailException;
import com.sumerge.authservice.exceptions.UserNotFoundException;
import com.sumerge.authservice.repository.UserRepository;
import com.sumerge.authservice.security.JwtGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtGeneratorService jwtGeneratorService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterNewUser() {

        User newUser = User.builder()
                .email("testemail@test.com")
                .password("password123")
                .build();

        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(newUser)).thenReturn(newUser);

        User registeredUser = userService.registerNewUser(newUser);

        assertEquals(newUser, registeredUser);
    }

    @Test(expected = NotValidMailException.class)
    public void testRegisterNewUserWithInvalidEmail() {

        User newUser = User.builder()
                .email("testemail.com")
                .password("password123")
                .build();

        //throw NotValidMailException
        userService.registerNewUser(newUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterNewUserWithExistingEmail() {
        User existingUser = User.builder()
                .email("testemail@test.com")
                .password("password123")
                .build();

        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        //  throw IllegalArgumentException alrady exists user
        userService.registerNewUser(existingUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterNewUserWithEmptyPassword() {

        User newUser = User.builder()
                .email("testemail@test.com")
                .password("")
                .build();

        //  throw IllegalArgumentException bad credentials
        userService.registerNewUser(newUser);

    }

    @Test
    public void testAuthenticateUser_FindUserAndReturnToken() {

        String password = "password123";
        String email = "existingemail@test.com";
        String hashedPassword = "$2a$12$HnTToAyLJr4NC6v3L6xFLeZ5..gu0C6KBC1GoxDz4DXdus8L.fL.W";

        User existingUser = User.builder()
                .email(email)
                .password(hashedPassword)
                .build();
        User newUser = User.builder()
                .email(email)
                .password(password)
                .build();

        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.of(existingUser));

        when(jwtGeneratorService.createToken(existingUser)).thenReturn("sampleToken");

        String token = userService.authenticateUser(newUser);

        assertEquals("sampleToken", token);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAuthenticateUser_BadCredentials() {

        String password = "password123";
        String email = "existingemail@test.com";

        User newUser = User.builder()
                .email(email)
                .password(password)
                .build();

        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.of(newUser));

        userService.authenticateUser(newUser);

    }

    @Test(expected = UserNotFoundException.class)
    public void testAuthenticateUser_WithNonExistingUser() {
        User nonExistingUser = User.builder()
                .email("nonexistingemail@test.com")
                .password("password123").build();

        when(userRepository.findByEmail(nonExistingUser.getEmail())).thenReturn(Optional.empty());

        userService.authenticateUser(nonExistingUser);
    }

}
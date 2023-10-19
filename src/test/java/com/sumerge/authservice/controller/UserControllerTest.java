package com.sumerge.authservice.controller;

import com.sumerge.authservice.entity.User;
import com.sumerge.authservice.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        User testUser = new User();
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("testpassword");

        when(userService.registerNewUser(any(User.class))).thenReturn(testUser);

        ResponseEntity<String> responseEntity = userController.registerUser(testUser);

        verify(userService, times(1)).registerNewUser(any(User.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertThat(responseEntity.getBody()).contains(testUser.getEmail());
    }

    @Test
    public void testLogin() {
        User testUser = new User();
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("testpassword");

        when(userService.authenticateUser(any(User.class))).thenReturn("authentication_token");

        ResponseEntity<String> responseEntity = userController.login(testUser);

        verify(userService, times(1)).authenticateUser(any(User.class));

        Assertions.assertThat(responseEntity.getBody()).isNotEmpty();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("authentication_token");
    }
}
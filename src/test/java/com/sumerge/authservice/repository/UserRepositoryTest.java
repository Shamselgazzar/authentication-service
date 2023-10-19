package com.sumerge.authservice.repository;

import com.sumerge.authservice.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Test
    public void findByEmail_findUser() {

        User user = User
                .builder()
                .email("testuser")
                .password("password123")
                .build();

        userRepository.save(user);

        User foundUser = userRepository.findByEmail(user.getEmail()).get();

        assertNotNull(foundUser);
        Assertions.assertThat(foundUser.getId()).isGreaterThan(0);
        assertEquals(user.getEmail(),foundUser.getEmail());

    }

    @Test
    void deleteUser() {

        Optional<User> user =  userRepository.findByEmail("testuser");
        userRepository.delete(user.get());

        assertTrue(userRepository.findByEmail("testuser").isEmpty());

    }

    @Test
    void findByEmail_notFoundUser() {

        Optional<User> user =  userRepository.findByEmail("--------");

        assertTrue(user.isEmpty());

    }

}

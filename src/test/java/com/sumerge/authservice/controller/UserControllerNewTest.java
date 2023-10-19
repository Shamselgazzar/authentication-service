//
//
//package com.sumerge.authservice.controller;
//
//import com.sumerge.authservice.AuthenticationServiceApplication;
//import com.sumerge.authservice.entity.User;
//import com.sumerge.authservice.repository.UserRepository;
//import org.junit.FixMethodOrder;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runners.MethodSorters;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@SpringBootTest(classes = AuthenticationServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
////@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//public class UserControllerNewTest {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private User testUser;
//
//    @BeforeEach
//    public void setUp() {
//        // You can create a test user for use in your tests
//        testUser = new User();
//        testUser.setEmail("testuser@example.com");
//        testUser.setPassword("testpassword");
//    }
//
//    @Test
//    public void testRegisterUser() {
//        // Create an HTTP headers object
//        HttpHeaders headers = new HttpHeaders();
//
//        // Create an HTTP entity with your test user as the request body and headers
//        HttpEntity<User> request = new HttpEntity<>(testUser, headers);
//
//        // Send an HTTP POST request to the /api/users/register endpoint
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                "/api/users/register", // The port is automatically handled by TestRestTemplate
//                HttpMethod.POST,
//                request,
//                String.class);
//
//        //userRepository.deleteUserByEmail(testUser.getEmail());
//
//        // Assert that the response is as expected
//        assertEquals(200, responseEntity.getStatusCode().value());
//        assertEquals("testuser@example.com : has been registered successfully!", responseEntity.getBody());
//    }
//
//    @Test
//    public void testLogin() {
//        // Assuming you have a test user registered, you can test the login endpoint
//        // Create an HTTP headers object
//        HttpHeaders headers = new HttpHeaders();
//
//        // Create an HTTP entity with your test user as the request body and headers
//        HttpEntity<User> request = new HttpEntity<>(testUser, headers);
//
//        // Send an HTTP POST request to the /api/users/login endpoint
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                "/api/users/login", // The port is automatically handled by TestRestTemplate
//                HttpMethod.POST,
//                request,
//                String.class);
//
//
//        // Assert that the response is as expected
//        assertEquals(200, responseEntity.getStatusCode().value());
//        // You can add more specific assertions for the login response
//    }
//}

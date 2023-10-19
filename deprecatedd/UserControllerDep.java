package com.sumerge.authservice.controller;

import com.sumerge.authservice.entity.User;
import com.sumerge.authservice.security.JwtService;
import com.sumerge.authservice.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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
    public User registerUser(@RequestBody User newUser) {
        System.out.println("in-controller-register");
        return userService.registerNewUser(newUser);
    }

    @PostMapping("/login")
    public String login(@RequestBody User newUser) {
        System.out.println("in-controller-login");
        return userService.authenticateUser(newUser);
    }

    @GetMapping ("/check")
    public Object check (
            //@RequestHeader() Map<String,String> headers
            @RequestHeader (name="authorization" , required = false) String authHeader

    ) {

        //String authHeader = headers.get("authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return "no Jwt token were found!";
        }

        return authHeader.substring(7);
    }

//    @GetMapping("/secure")
//    public ResponseEntity<String> secureEndpoint(@NonNull HttpServletRequest request) {
//        // Get the token from the request header
//        String token = request.getHeader("authorization");
//
//        if (token != null && token.startsWith("Bearer ")) {
//            // Remove "Bearer " prefix
//            token = token.substring(7);
//
//            try {
//                // Validate and process the token
//                Claims claims = Jwts.parserBuilder()
//                        .setSigningKey(jwtService.getSigningKey()) // Replace with your secret key
//                        .build()
//                        .parseClaimsJws(token)
//                        .getBody();
//
//                String email = claims.getSubject(); // Extract the username from the claims
//
//                // Additional validation and processing as needed
//                // ...
//
//                return ResponseEntity.ok("Authenticated as: " + email);
//            } catch (Exception e) {
//                // Handle token validation failure
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//            }
//        }
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
//    }


}


//generate key::
//return secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
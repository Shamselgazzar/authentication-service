package com.sumerge.authservice.service;

import com.sumerge.authservice.entity.User;

public interface UserService {
    User registerNewUser(User newUser);

    String authenticateUser(User newUser);
}

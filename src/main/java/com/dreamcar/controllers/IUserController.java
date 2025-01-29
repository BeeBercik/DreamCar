package com.dreamcar.controllers;

import com.dreamcar.dto.UserRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

public interface IUserController {
    ResponseEntity<?> registerUser(UserRequest userRequest);
    ResponseEntity<?> loginUser(UserRequest userRequest, HttpSession session);
    ResponseEntity<?> logoutUser(HttpSession session);
    ResponseEntity<?> isUserLoggedIn(HttpSession session);
}

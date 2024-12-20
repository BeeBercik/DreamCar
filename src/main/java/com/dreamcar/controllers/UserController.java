package com.dreamcar.controllers;

import com.dreamcar.dto.UserRequest;
import com.dreamcar.exceptions.IncorrectLoginDataException;
import com.dreamcar.exceptions.IncorrectRegisterDataException;
import com.dreamcar.exceptions.UserNotLoggedInException;
import com.dreamcar.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        try {
            this.userService.registerUser(userRequest);
            return ResponseEntity.ok("User registered successfully");
        } catch (IncorrectRegisterDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody UserRequest userRequest, HttpSession session) {
        try {
            session.setAttribute("user", this.userService.loginUser(userRequest));
            return ResponseEntity.ok("User logged in");
        } catch(IncorrectLoginDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<?> logoutUser(HttpSession session) {
        try {
            this.userService.getLoggedUser(session);
            session.invalidate();
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("User logged out");
    }

    @GetMapping("/getLoggedUser")
    public ResponseEntity<?> isUserLoggedIn(HttpSession session) {
        try {
           return ResponseEntity.ok(this.userService.getLoggedUser(session));
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

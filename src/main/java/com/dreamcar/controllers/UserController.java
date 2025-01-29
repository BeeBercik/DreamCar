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

/**
 * Rest controller responsible for handling requests related to users from frontend and generating response from backend
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Endpoint for registering new user.
     *
     * @param userRequest user data provided from frontend
     * @return response object with message with 200 status code if user was successfully registered or 400 if there was an error
     */
    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        try {
            this.userService.registerUser(userRequest);
            return ResponseEntity.ok("User registered successfully");
        } catch (IncorrectRegisterDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint for logging user
     *
     * @param userRequest user data provided from frontend
     * @param session used to store the logged user
     * @return  response object with message with 200 status code if user was logged in successfully or 400 if there was an error
     */
    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody UserRequest userRequest, HttpSession session) {
        try {
            session.setAttribute("user", this.userService.loginUser(userRequest));
            return ResponseEntity.ok("User logged in");
        } catch(IncorrectLoginDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint responsible for log out user.
     *
     * @param session used to log out user
     * @return  response object with message with 200 status code if user was logged out successfully or 400 if there was an error
     */
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

    /**
     * Endpoint for getting already logged user
     *
     * @param session used to get logged user stored in that object
     * @return response object with user data with 200 status code if user was logged in or 401 if user was not logged in
     */
    @GetMapping("/getLoggedUser")
    public ResponseEntity<?> isUserLoggedIn(HttpSession session) {
        try {
           return ResponseEntity.ok(this.userService.convertUserToResponse(this.userService.getLoggedUser(session)));
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

package com.dreamcar.controllers;

import com.dreamcar.dto.UserDTO;
import com.dreamcar.exceptions.IncorrectLoginDataException;
import com.dreamcar.exceptions.IncorrectRegisterDataException;
import com.dreamcar.exceptions.UserNotLoggedInException;
import com.dreamcar.model.Offer;
import com.dreamcar.model.User;
import com.dreamcar.repositories.UserRepository;
import com.dreamcar.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            this.userService.registerUser(userDTO);
            return ResponseEntity.ok("User registered successfully");
        } catch (IncorrectRegisterDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody User user, HttpSession session) {
        try {
            UserDTO userDTO = this.userService.loginUser(user);
            session.setAttribute("user", userDTO);

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

    @GetMapping("/getUserOffers")
    public ResponseEntity<?> getUserOffers(HttpSession session) {
        try {
            User user = this.userService.getLoggedUser(session);
            List<Offer> userOffers = user.getOffers();

            return ResponseEntity.ok(userOffers);
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getLoggedUser")
    public ResponseEntity<?> isUserLoggedIn(HttpSession session) {
        try {
           User user = this.userService.getLoggedUser(session);
           return ResponseEntity.ok(user);
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

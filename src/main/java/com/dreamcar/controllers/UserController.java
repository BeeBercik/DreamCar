package com.dreamcar.controllers;

import com.dreamcar.dto.UserDTO;
import com.dreamcar.model.Offer;
import com.dreamcar.model.User;
import com.dreamcar.repositories.UserRepository;
import com.dreamcar.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody User user, HttpSession session) {
        try {
            UserDTO userDTO = this.userService.loginUser(user);
            session.setAttribute("user", userDTO);
            return ResponseEntity.ok("User logged in");
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/isUserLoggedIn")
    public ResponseEntity<?> isUserLoggedIn(HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(userDTO != null) {
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.badRequest().body("Not logged in");
    }

    @GetMapping("/logoutUser")
    public ResponseEntity<?> logoutUser(HttpSession session) {
        session.invalidate();

        return ResponseEntity.ok("User logged out");
    }

    @GetMapping("/getUserOffers")
    public ResponseEntity<?> getUserOffers(HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(userDTO != null) {
            User user = this.userRepository.findById(userDTO.getId()).get();
            List<Offer> userOffers = user.getOffers();

            return ResponseEntity.ok(userOffers);
        } else return ResponseEntity.badRequest().body("User not logged in");
    }
}

package com.dreamcar.services;

import com.dreamcar.model.User;
import com.dreamcar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> registerUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        if(userRepository.existsByLogin(user.getLogin())) {
            return ResponseEntity.badRequest().body("Login already exists");
        }
//        if(!user.getPassword().equals()) {
//
//        }
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}

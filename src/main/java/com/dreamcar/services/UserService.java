package com.dreamcar.services;

import com.dreamcar.model.User;
import com.dreamcar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> registerUser(User user) {
        if(user.getLogin() == null || user.getLogin().trim().isEmpty() ||
                user.getPassword() == null || user.getPassword().trim().isEmpty() ||
                user.getEmail() == null || user.getEmail().trim().isEmpty() ||
                user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Incorrect data");
        }
        if(user.getLogin().trim().length() < 5) {
            return ResponseEntity.badRequest().body("Login length must be at least 5 characters ");
        }
        if(user.getPassword().trim().length() < 5) {
            return ResponseEntity.badRequest().body("Password length must be at least 5 characters ");
        }
        if(user.getPhone().trim().length() < 9) {
            return ResponseEntity.badRequest().body("Phone length must be at least 9 characters ");
        }
        if(userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        if(userRepository.existsByLogin(user.getLogin())) {
            return ResponseEntity.badRequest().body("Login already exists");
        }
//        if(!user.getPassword().equals()) {
//
//        }
        user.setAdd_date(new Date());
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}

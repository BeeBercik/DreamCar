package com.dreamcar.services;

import com.dreamcar.model.User;
import com.dreamcar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserValidatior {

    public void validate(User user) {
        if(user.getLogin() == null || user.getLogin().trim().isEmpty() ||
                user.getPassword() == null || user.getPassword().trim().isEmpty() ||
                user.getEmail() == null || user.getEmail().trim().isEmpty() ||
                user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Incorrect data");
        }
        if(user.getLogin().trim().length() < 5) {
            throw new IllegalArgumentException("Login length must be at least 5 characters ");
        }
        if(user.getPassword().trim().length() < 5) {
            throw new IllegalArgumentException("Password length must be at least 5 characters ");
        }
        if(user.getPhone().trim().length() < 9) {
            throw new IllegalArgumentException("Phone length must be at least 9 characters ");
        }
//        if(!user.getPassword().equals()) {
//
//        }
    }
}

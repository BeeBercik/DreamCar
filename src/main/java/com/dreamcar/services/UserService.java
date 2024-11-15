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

    @Autowired
    UserValidatior userValidatior;

    public void registerUser(User user) {
        userValidatior.validate(user);

        if(userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if(userRepository.existsByLogin(user.getLogin())) {
            throw new IllegalArgumentException("Login already exists");
        }

        user.setAdd_date(new Date());
        userRepository.save(user);
    }
}

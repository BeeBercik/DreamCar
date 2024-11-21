package com.dreamcar.services;

import com.dreamcar.dto.UserDTO;
import com.dreamcar.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {

    public void validateUserRegistration(UserDTO userDTO) {
        if(userDTO.getLogin() == null || userDTO.getLogin().trim().isEmpty() ||
                userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty() ||
                userDTO.getRep_password() == null || userDTO.getRep_password().trim().isEmpty() ||
                userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty() ||
                userDTO.getPhone() == null || userDTO.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Incorrect data");
        }
        if(userDTO.getLogin().trim().length() < 5) {
            throw new IllegalArgumentException("Login length must be at least 5 characters ");
        }
        if(userDTO.getPassword().trim().length() < 5) {
            throw new IllegalArgumentException("Password length must be at least 5 characters ");
        }
        if(userDTO.getPhone().trim().length() < 9) {
            throw new IllegalArgumentException("Phone length must be at least 9 characters ");
        }
        if(!userDTO.getPassword().equals(userDTO.getRep_password())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }

    public void validateLogin(User user) {
        if(user.getLogin() == null || user.getLogin().trim().isEmpty() ||
        user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Incorrect data");
        }
    }
}

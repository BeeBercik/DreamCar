package com.dreamcar.services;

import com.dreamcar.dto.UserRequest;
import com.dreamcar.exceptions.IncorrectLoginDataException;
import com.dreamcar.exceptions.IncorrectRegisterDataException;
import org.springframework.stereotype.Service;

/**
 * Class which validates user registration and login data
 */
@Service
public class UserValidator {

    /**
     * Method validates user registration data with specific requirements
     *
     * @param userRequest passed data from html form
     * @throws IncorrectRegisterDataException if any of the condition is not met
     */
    public void validateUserRegistration(UserRequest userRequest) {
        if(userRequest.getLogin() == null || userRequest.getLogin().trim().isEmpty() ||
                userRequest.getPassword() == null || userRequest.getPassword().trim().isEmpty() ||
                userRequest.getRep_password() == null || userRequest.getRep_password().trim().isEmpty() ||
                userRequest.getEmail() == null || userRequest.getEmail().trim().isEmpty() ||
                userRequest.getPhone() == null || userRequest.getPhone().trim().isEmpty()) {
            throw new IncorrectRegisterDataException("Incorrect data");
        }
        if(userRequest.getLogin().trim().length() < 5) {
            throw new IncorrectRegisterDataException("Login length must be at least 5 characters ");
        }
        if(userRequest.getPassword().trim().length() < 5) {
            throw new IncorrectRegisterDataException("Password length must be at least 5 characters ");
        }
        if(userRequest.getPhone().trim().length() < 9) {
            throw new IncorrectRegisterDataException("Phone length must be at least 9 characters ");
        }
        if(!userRequest.getPassword().equals(userRequest.getRep_password())) {
            throw new IncorrectRegisterDataException("Passwords do not match");
        }
    }

    /**
     * Validates user login data
     *
     * @param userRequest passed data from html form
     * @throws IncorrectLoginDataException if any of the condition is not met
     */
    public void validateLogin(UserRequest userRequest) {
        if(userRequest.getLogin() == null || userRequest.getLogin().trim().isEmpty() ||
                userRequest.getPassword() == null || userRequest.getPassword().trim().isEmpty())
            throw new IncorrectLoginDataException("Incorrect data");
    }
}

package com.dreamcar.services;

import com.dreamcar.dto.UserRequest;

public interface IUserValidator {
    void validateUserRegistration(UserRequest userRequest);
    void validateLogin(UserRequest userRequest);
}

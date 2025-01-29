package com.dreamcar.services;

import com.dreamcar.dto.UserRequest;
import com.dreamcar.dto.UserResponse;
import com.dreamcar.model.User;
import jakarta.servlet.http.HttpSession;

public interface IUserService {
    void registerUser(UserRequest userRequest);
    UserResponse loginUser(UserRequest userRequest);
    User getLoggedUser(HttpSession session);
    UserResponse convertUserToResponse(User user);
}

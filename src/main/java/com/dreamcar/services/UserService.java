package com.dreamcar.services;

import com.dreamcar.dto.UserRequest;
import com.dreamcar.dto.UserResponse;
import com.dreamcar.exceptions.IncorrectLoginDataException;
import com.dreamcar.exceptions.IncorrectRegisterDataException;
import com.dreamcar.exceptions.UserNotLoggedInException;
import com.dreamcar.model.User;
import com.dreamcar.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.NoSuchElementException;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserValidator userValidator;

    public void registerUser(UserRequest userRequest) {
        userValidator.validateUserRegistration(userRequest);

        if(userRepository.existsByEmail(userRequest.getEmail())) {
            throw new IncorrectRegisterDataException("Email already exists");
        }
        if(userRepository.existsByLogin(userRequest.getLogin())) {
            throw new IncorrectRegisterDataException("Login already exists");
        }

        userRepository.save(new User(
                userRequest.getLogin(),
                BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt()),
                userRequest.getEmail(),
                userRequest.getPhone(),
                new Date()
        ));
    }

    public UserResponse loginUser(UserRequest userRequest) {
        this.userValidator.validateLogin(userRequest);

        if(userRepository.existsByLogin(userRequest.getLogin().trim())) {
            User dbuser = userRepository.findByLogin(userRequest.getLogin());
            if(BCrypt.checkpw(userRequest.getPassword(), dbuser.getPassword().trim())) {
                return new UserResponse(
                        dbuser.getId(),
                        dbuser.getLogin(),
                        dbuser.getEmail(),
                        dbuser.getPhone(),
                        dbuser.getAdd_date());
            } else throw new IncorrectLoginDataException("Wrong password");
        } else throw new IncorrectLoginDataException("Login does not exist");
    }

    public User getLoggedUser(HttpSession session) {
        UserResponse userResponse = (UserResponse) session.getAttribute("user");
        if(userResponse == null) throw new UserNotLoggedInException("User not logged in");

        return this.userRepository.findById(userResponse.getId()).orElseThrow(() -> new NoSuchElementException("User with such id does not exist"));
    }

    public UserResponse convertUserToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getLogin(),
                user.getEmail(),
                user.getPhone(),
                user.getAdd_date()
        );
    }
}

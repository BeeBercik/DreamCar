package com.dreamcar.services.impl;

import com.dreamcar.dto.UserRequest;
import com.dreamcar.dto.UserResponse;
import com.dreamcar.exceptions.IncorrectLoginDataException;
import com.dreamcar.exceptions.IncorrectRegisterDataException;
import com.dreamcar.exceptions.UserNotLoggedInException;
import com.dreamcar.model.User;
import com.dreamcar.repositories.UserRepository;
import com.dreamcar.services.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;

/**
 * Service responsible for logic operations with users
 */
@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    /**
     *  Registers user to the app
     *
     * @param userRequest provided user data from html registration form
     * @throws IncorrectRegisterDataException if any of the condition is not met
     */
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

    /**
     * Logins user to the app
     *
     * @param userRequest provided user data from login html form
     * @return dto user class object without password field
     * @throws IncorrectLoginDataException if user provided wrong password
     * @throws IncorrectLoginDataException if user login does not exist in database
     */
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

    /**
     * Method looks for logged user in session
     *
     * @param session stores logged user
     * @return user object with its favourite list etc.
     * @throws UserNotLoggedInException if user does not exist in session
     * @throws NoSuchElementException if user stored in session does not exist in database
     */
    public User getLoggedUser(HttpSession session) {
        UserResponse userResponse = (UserResponse) session.getAttribute("user");
        if(userResponse == null) throw new UserNotLoggedInException("User not logged in");

        return this.userRepository.findById(userResponse.getId()).orElseThrow(() -> new NoSuchElementException("User with such id does not exist"));
    }

    /**
     * Coverts user entity object to safe user dto class
     *
     * @param user given user entity object
     * @return safe user dto class without password
     */
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

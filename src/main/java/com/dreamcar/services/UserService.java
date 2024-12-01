package com.dreamcar.services;

import com.dreamcar.dto.UserDTO;
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

    public void registerUser(UserDTO userDTO) {
        userValidator.validateUserRegistration(userDTO);

        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IncorrectRegisterDataException("Email already exists");
        }
        if(userRepository.existsByLogin(userDTO.getLogin())) {
            throw new IncorrectRegisterDataException("Login already exists");
        }
        User user = new User();
        user.setLogin(userDTO.getLogin());
        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAdd_date(new Date());

        userRepository.save(user);
    }

    public UserDTO loginUser(User user) {
        this.userValidator.validateLogin(user);

        if(userRepository.existsByLogin(user.getLogin().trim())) {
            User dbuser = userRepository.findByLogin(user.getLogin());
            if(BCrypt.checkpw(user.getPassword(), dbuser.getPassword().trim())) {
                return new UserDTO(dbuser.getId(),
                        dbuser.getLogin(), dbuser.getEmail(),
                        dbuser.getPhone(), dbuser.getAdd_date());
            } else throw new IncorrectLoginDataException("Wrong password");
        } else throw new IncorrectLoginDataException("Login does not exist");
    }

    public User getLoggedUser(HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(userDTO == null) throw new UserNotLoggedInException("User not logged in");

        return this.userRepository.findById(userDTO.getId()).orElseThrow(() -> new NoSuchElementException("User with such id does not exist"));
    }
}
